package com.nsr.osama.assetmanager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.nsr.osama.assetmanager.database.EntryViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        var categoryCount = 1
        lateinit var entryViewModel: EntryViewModel
//        lateinit var currentAdapter: MyAdapter
    }

    /**
     * The [androidx.viewpager.widget.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * androidx.fragment.app.FragmentStatePagerAdapter.
     */
    private lateinit var mSectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager).also {
            // Set up the ViewPager with the sections adapter.
            container.adapter = it
        }

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        fab.setOnClickListener {
            EntryBSDF.newInstance(EntryBSDF.OperationType.Insert).show(supportFragmentManager)
        }


        MainActivity.entryViewModel = ViewModelProviders.of(this).get(EntryViewModel::class.java)
//        MainActivity.entryViewModel.entries.observe(this, Observer {
//            // my not be initialized yet
//            MainActivity.currentAdapter?.submitList(it.filter { it2 -> it2.category == mSectionsPagerAdapter.currentPos })
//        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_settings -> true
        else -> super.onOptionsItemSelected(item)
    }
}

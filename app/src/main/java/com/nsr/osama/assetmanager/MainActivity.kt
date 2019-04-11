package com.nsr.osama.assetmanager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.nsr.osama.assetmanager.database.EntryModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        var arrayListData = ArrayList<EntryModel>().apply {
            add(EntryModel(Date(), 0.0, "0"))
            add(EntryModel(Date(), 1.0, "1"))
            add(EntryModel(Date(), 2.0, "2"))
            add(EntryModel(Date(), 3.0, "3"))
            add(EntryModel(Date(), 3.0, "3", quantity = 3, category = 1))
            add(EntryModel(Date(), 20.5, "20.5", quantity = 2, category = 1))
            add(EntryModel(Date(), 75.0, "75", quantity = 1, category = 1))
            add(EntryModel(Date(), 3.0, "3", quantity = 20, category = 1))
            add(EntryModel(Date(), 8.0, "8", quantity = 5, category = 1))
            add(EntryModel(Date(), 10.0, "10", quantity = 2, category = 1))
        }
        var categoryCount = 2
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
            //            Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            AddEntryBSDF().show(supportFragmentManager, "TAG_fragment_add_entry_bottom_sheet")
        }

//        val entryViewModel = ViewModelProviders.of(this).get(EntryViewModel::class.java)
//        entryViewModel.entries.observe(this, androidx.lifecycle.Observer {
//            MainActivity.arrayListData = it
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

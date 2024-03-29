package com.nsr.osama.assetmanager

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.animation.Easing
import com.google.android.material.tabs.TabLayout
import com.nsr.osama.assetmanager.database.EntryModel
import com.nsr.osama.assetmanager.database.EntryViewModel
import com.nsr.osama.assetmanager.pie_chart.PieChartInterface
import com.nsr.osama.assetmanager.pie_chart.PieChartListener
import com.nsr.osama.assetmanager.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        var categoryCount: Byte = 1
        lateinit var entryViewModel: EntryViewModel
        lateinit var PieChart: PieChartInterface
    }


    private lateinit var sectionPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        pieChart.apply {
            description.isEnabled = false
            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)
            extraBottomOffset = -200f
            maxAngle = 180f
            rotationAngle = 180f
            isRotationEnabled = false
            scaleY = 0.85f
        }
        MainActivity.PieChart = PieChartListener(pieChart, collapsingToolbarLayout)

        MainActivity.entryViewModel = ViewModelProviders.of(this).get(EntryViewModel::class.java)
        MainActivity.entryViewModel.entries.observe(this, object : Observer<List<EntryModel>> {
            override fun onChanged(_list: List<EntryModel>) {
                MainActivity.entryViewModel.entries.removeObserver(this)
                categoryCount = entryViewModel.entries.value?.maxBy { it.category }?.category?.inc() ?: 1

                // Create the adapter that will return a fragment for each of the sections of the activity.
                sectionPagerAdapter = SectionsPagerAdapter(supportFragmentManager).also {
                    // Set up the ViewPager with the sections adapter.
                    container.adapter = it
                    container.addOnPageChangeListener(it)
                }

                for (i in 1 until categoryCount) {
                    tabs.addTab(tabs.newTab().setText(i.toString()))
                }
                MainActivity.PieChart.updatePieChart(_list.filter { it.category == 0.toByte() },true)
            }
        })


        fab.setOnClickListener {
            EntryBSDF.newInstance(EntryBSDF.OperationType.Insert, Bundle().apply {
                putByte("category", sectionPagerAdapter.currentPos.toByte())
            }).show(supportFragmentManager)
        }

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
        R.id.action_add_new_category -> {
            EntryBSDF.newInstance(EntryBSDF.OperationType.Insert, Bundle().apply {
                putBoolean("isNewCategory", true)
                putByte("category", MainActivity.categoryCount)
            }) {
                tabs.addTab(tabs.newTab().setText(MainActivity.categoryCount.toString()))
                MainActivity.categoryCount = MainActivity.categoryCount.inc()
                sectionPagerAdapter.notifyDataSetChanged()
            }.show(supportFragmentManager)
            true
        }
        R.id.action_settings -> {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}

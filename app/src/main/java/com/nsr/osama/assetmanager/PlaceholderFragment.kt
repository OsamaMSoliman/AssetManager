package com.nsr.osama.assetmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nsr.osama.assetmanager.database.EntryViewModel
import com.nsr.osama.assetmanager.recycler_view.MyAdapter
import kotlinx.android.synthetic.main.fragment_main.view.*

class PlaceholderFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
//        rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(PlaceholderFragment.SECTION_POSITION_NUMBER))
//        Toast.makeText(activity, "New Category #(${arguments?.getByte(PlaceholderFragment.SECTION_POSITION_NUMBER).toString()})", Toast.LENGTH_SHORT).show()
        val recyclerViewAdapter: MyAdapter = MyAdapter(activity?.supportFragmentManager).also {
            rootView.recyclerView.adapter = it
            rootView.recyclerView.layoutManager = LinearLayoutManager(activity)
            rootView.recyclerView.setHasFixedSize(true)
//            MainActivity.currentAdapter = it
        }

        var skip = true
        ViewModelProviders.of(this).get(EntryViewModel::class.java)
                .entries.observe(this, androidx.lifecycle.Observer {
            val pos = arguments?.getByte(PlaceholderFragment.SECTION_POSITION_NUMBER)
            val filteredList = it.filter { it2 -> it2.category == pos }
            recyclerViewAdapter.submitList(filteredList)
            if (skip) {
                skip = false
                return@Observer
            }
            MainActivity.PieChart.updatePieChart(filteredList, false)
        })
        return rootView
    }

    companion object {
        private const val SECTION_POSITION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Byte): PlaceholderFragment =
                PlaceholderFragment().also {
                    it.arguments = Bundle().apply {
                        putByte(SECTION_POSITION_NUMBER, sectionNumber)
                    }
                }
    }
}
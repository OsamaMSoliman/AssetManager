package com.nsr.osama.assetmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nsr.osama.assetmanager.database.EntryModel
import com.nsr.osama.assetmanager.recycler_view.MyAdapter
import kotlinx.android.synthetic.main.fragment_main.view.*

class PlaceholderFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
//        rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(ARG_SECTION_NUMBER))
        rootView.recyclerView.let {
            val pos = arguments?.getInt(ARG_SECTION_NUMBER)
            it.adapter = MyAdapter(MainActivity.arrayListData.filter { it2 -> it2.category == pos } as MutableList<EntryModel>)
            it.layoutManager = LinearLayoutManager(activity)
            it.setHasFixedSize(true)
        }
        return rootView
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Int): PlaceholderFragment =
                PlaceholderFragment().also {
                    it.arguments = Bundle().also { it2 ->
                        it2.putInt(ARG_SECTION_NUMBER, sectionNumber)
                    }
                }
    }
}
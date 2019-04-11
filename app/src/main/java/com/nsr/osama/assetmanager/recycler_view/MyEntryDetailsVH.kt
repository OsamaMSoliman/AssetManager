package com.nsr.osama.assetmanager.recycler_view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.entry_details.view.*

class MyEntryDetailsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val dateTextView: TextView = itemView.dateTextView
    val descriptionTextView: TextView = itemView.descriptionTextView
    val quantityTextView: TextView = itemView.quantityTextView
}
package com.nsr.osama.assetmanager.recycler_view

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.entry.view.*

class MyEntryVH(itemView: View, listener: PlaceHolderInterface) : RecyclerView.ViewHolder(itemView) {

    val idTextView: TextView = itemView.idTextView
    val signImageView: ImageView = itemView.signImageView
    val moneyTextView: TextView = itemView.moneyTextView
    val quantityTextView: TextView = itemView.quantityTextView
    private val editImgBtn: ImageButton = itemView.editImgBtn
    private val deleteImgBtn: ImageButton = itemView.deleteImgBtn

    init {
        itemView.setOnClickListener {
            listener.registerMovement(adapterPosition)
        }
    }

    fun ToggleBtns(show: Boolean) {
        if (show) {
            editImgBtn.visibility = View.VISIBLE
            deleteImgBtn.visibility = View.VISIBLE
            quantityTextView.visibility = View.GONE
        } else {
            editImgBtn.visibility = View.GONE
            deleteImgBtn.visibility = View.GONE
            quantityTextView.visibility = View.VISIBLE
        }
    }
}

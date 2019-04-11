package com.nsr.osama.assetmanager.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nsr.osama.assetmanager.R
import com.nsr.osama.assetmanager.database.EntryModel

//class MyAdapter(/*private val list: MutableList<EntryModel>*/) : ListAdapter<EntryModel, RecyclerView.ViewHolder>(
//        object : DiffUtil.ItemCallback<EntryModel>() {
//            override fun areItemsTheSame(oldItem: EntryModel, newItem: EntryModel): Boolean = oldItem.id == newItem.id
//
//            override fun areContentsTheSame(oldItem: EntryModel, newItem: EntryModel): Boolean = oldItem == newItem
//        }
//) {

class MyAdapter(private val list: MutableList<EntryModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val subEntry by lazy {
        EntryDetailsLocationLogic(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.entry -> MyEntryVH(inflater.inflate(viewType, parent, false), subEntry)
            R.layout.entry_details -> MyEntryDetailsVH(inflater.inflate(viewType, parent, false))
            else -> throw IndexOutOfBoundsException("NO SUCH LAYOUT!: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyEntryVH -> {
                holder.ToggleBtns(subEntry.currentPosition != -1 && position == subEntry.currentPosition - 1)
                val newPos = if (subEntry.currentPosition != -1 && position > subEntry.currentPosition) position - 1 else position
                holder.idTextView.text = newPos.toString()
                holder.moneyTextView.text = list[newPos].price.toString()
            }
            is MyEntryDetailsVH -> {
                holder.dateTextView.text = list[position - 1].time.toString()
                holder.quantityTextView.text = list[position - 1].quantity.toString()
                holder.descriptionTextView.text = list[position - 1].description
            }
        }
    }

    override fun getItemCount(): Int =
            if (subEntry.currentPosition == -1) list.size else list.size + 1

    override fun getItemViewType(position: Int): Int =
            if (position == subEntry.currentPosition) R.layout.entry_details else R.layout.entry
}
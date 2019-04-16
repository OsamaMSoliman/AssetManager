package com.nsr.osama.assetmanager.recycler_view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nsr.osama.assetmanager.EntryBSDF
import com.nsr.osama.assetmanager.R
import com.nsr.osama.assetmanager.database.EntryModel

class MyAdapter(private val fm: FragmentManager?) : ListAdapter<EntryModel, RecyclerView.ViewHolder>(
        object : DiffUtil.ItemCallback<EntryModel>() {
            override fun areItemsTheSame(oldItem: EntryModel, newItem: EntryModel): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: EntryModel, newItem: EntryModel): Boolean = oldItem == newItem
        }
) {

    private val subEntry by lazy {
        EntryDetailsLocationLogic(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.entry -> MyEntryVH(inflater.inflate(viewType, parent, false), subEntry).apply {
                editImgBtn.setOnClickListener { edit_delete(this, EntryBSDF.OperationType.Update) }
                deleteImgBtn.setOnClickListener { edit_delete(this, EntryBSDF.OperationType.Delete) }
            }
            R.layout.entry_details -> MyEntryDetailsVH(inflater.inflate(viewType, parent, false))
            else -> throw IndexOutOfBoundsException("NO SUCH LAYOUT!: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyEntryVH -> {
                holder.ToggleBtns(subEntry.currentPosition != -1 && position == subEntry.currentPosition - 1)
                val newPos = if (subEntry.currentPosition != -1 && position > subEntry.currentPosition) position - 1 else position
                holder.positionTextView.text = newPos.toString()
                holder.moneyTextView.text = getItem(newPos).price.toString()
                holder.signImageView.setImageResource(if (getItem(newPos).isIncrease) android.R.drawable.arrow_up_float else android.R.drawable.arrow_down_float)
                holder.signImageView.setColorFilter(if (getItem(newPos).isIncrease) Color.GREEN else Color.RED)
                holder.quantityTextView.text = getItem(newPos).quantity.toString()
            }
            is MyEntryDetailsVH -> {
                holder.dateTextView.text = getItem(position - 1).time.toString()
                holder.quantityTextView.text = getItem(position - 1).quantity.toString()
                holder.descriptionTextView.text = getItem(position - 1).description
            }
        }
    }

    override fun getItemCount(): Int =
            super.getItemCount() + if (subEntry.currentPosition == -1) 0 else 1

    override fun getItemViewType(position: Int): Int =
            if (position == subEntry.currentPosition) R.layout.entry_details else R.layout.entry

    private fun edit_delete(v: MyEntryVH, opType: EntryBSDF.OperationType) = fm?.let {
        EntryBSDF.newInstance(opType, getItem(v.positionTextView.text.toString().toInt())).show(fm)
    }
}

package com.nsr.osama.assetmanager.recycler_view

class EntryDetailsLocationLogic(private val adapter: MyAdapter) : PlaceHolderInterface {
    var currentPosition = -1
    override fun registerMovement(adapterPosition: Int) {
        val newPos = adapterPosition + 1
//        var oldPos = -1 // TODO: using this always causes a logical error!
        when (currentPosition) {
            //Todo: if not present, show it
            -1 -> {
                currentPosition = newPos
                adapter.notifyItemInserted(currentPosition)
            }
            //Todo: if present on the same item, hide it
            newPos -> {
                val isNotUsed = currentPosition == -1
                currentPosition = if (isNotUsed) newPos else -1
                if (isNotUsed) adapter.notifyItemInserted(newPos)
                else adapter.notifyItemRemoved(newPos)
            }
            //Todo: if present on different item
            else -> currentPosition =
                    if (newPos == adapter.itemCount || currentPosition < newPos)
                        adapterPosition else newPos

        }
        adapter.notifyItemRangeChanged(0, adapter.itemCount)
    }
}
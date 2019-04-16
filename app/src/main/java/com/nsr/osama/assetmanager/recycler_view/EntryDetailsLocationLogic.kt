package com.nsr.osama.assetmanager.recycler_view

class EntryDetailsLocationLogic(private val adapter: MyAdapter) : PlaceHolderInterface {
    var currentPosition = -1
    override fun registerMovement(adapterPosition: Int) {
        val newPos = adapterPosition + 1
        var oldPos = currentPosition
        when (currentPosition) {
            //Todo: if not present, show it
            -1 -> {
                currentPosition = newPos
                adapter.notifyItemInserted(currentPosition)
                adapter.notifyItemChanged(currentPosition - 1)
            }
            //Todo: if present on the same item, hide it
            newPos -> {
                if (currentPosition == -1) {
                    currentPosition = newPos
                    adapter.notifyItemInserted(newPos)
                    adapter.notifyItemChanged(currentPosition - 1)
                } else {
                    currentPosition = -1
                    adapter.notifyItemRemoved(newPos)
                    adapter.notifyItemChanged(oldPos - 1)
                }
            }
            //Todo: if present on different item
            else -> {
                currentPosition =
                        if (newPos == adapter.itemCount || currentPosition < newPos)
                            adapterPosition else newPos
                oldPos = if (oldPos == adapter.itemCount - 1 || currentPosition < oldPos)
                    oldPos else oldPos - 1
                adapter.notifyItemMoved(oldPos, currentPosition)
                adapter.notifyItemChanged(currentPosition)
                adapter.notifyItemChanged(oldPos)
                adapter.notifyItemChanged(currentPosition - 1)
            }
        }
    }
}
package com.nsr.osama.assetmanager.database

import android.os.Bundle
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Entries")
data class EntryModel(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,
        val time: Date,
        val price: Double,
        val description: String,
        val quantity: Int,
        val category: Byte,
        val isIncrease: Boolean) {
    @Ignore
    constructor(time: Date,
                price: Double,
                description: String,
                quantity: Int = 1,
                category: Byte = 0,
                isIncrease: Boolean = false) : this(null, time, price, description, quantity, category, isIncrease)


    fun toBundle() = Bundle().apply {
        id?.let { putInt("id", id) }
        putLong("time", MyRoomConverters.dateToTimestamp(time)!!)
        putDouble("price", price)
        putString("description", description)
        putInt("quantity", quantity)
        putByte("category", category)
        putBoolean("isIncrease", isIncrease)
    }
}
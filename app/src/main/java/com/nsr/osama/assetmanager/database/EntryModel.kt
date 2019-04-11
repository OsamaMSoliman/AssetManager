package com.nsr.osama.assetmanager.database

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
        val quantity: Int = 1,
        val description: String,
        val category: Int = 0) {
    @Ignore
    constructor(time: Date,
                price: Double,
                description: String,
                quantity: Int = 1,
                category: Int = 0) : this(null, time, price, quantity, description, category)
}
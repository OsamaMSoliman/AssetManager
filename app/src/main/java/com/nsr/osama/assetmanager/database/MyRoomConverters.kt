package com.nsr.osama.assetmanager.database

import androidx.room.TypeConverter
import java.util.*

object MyRoomConverters {

    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): Date? = if (value == null) null else Date(value)


    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): Long? = date?.time

}
package com.nsr.osama.assetmanager.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(EntryModel::class), version = 1, exportSchema = false)
@TypeConverters(MyRoomConverters::class)
abstract class EntryDB : RoomDatabase() {

    abstract fun entryDao(): EntryDao

    companion object {
        @Volatile
        private var sInstance: EntryDB? = null

        fun getInstance(context: Context): EntryDB = sInstance ?: synchronized(EntryDB::class) {
            Log.d(EntryDB::class.java.simpleName, "Creating new database instance")
            sInstance ?: Room.databaseBuilder(context.applicationContext,
                    EntryDB::class.java, "AssetManager")
                    .build().apply {
                        sInstance = this
                    }
        }

        fun destroyDB() {
            sInstance = null
        }
    }
}
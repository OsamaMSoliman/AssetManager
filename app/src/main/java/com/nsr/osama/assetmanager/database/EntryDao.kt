package com.nsr.osama.assetmanager.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EntryDao {
    @Insert
    fun insert(entryModel: EntryModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entryModel: EntryModel)

    @Delete
    fun delete(entryModel: EntryModel)

    @Query("SELECT * FROM Entries")
    fun query(): LiveData<ArrayList<EntryModel>>
}
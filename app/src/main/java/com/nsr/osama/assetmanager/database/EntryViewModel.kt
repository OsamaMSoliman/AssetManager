package com.nsr.osama.assetmanager.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class EntryViewModel(application: Application) : AndroidViewModel(application) {
    private val entryRepository = EntryRepository(application)
    val entries: LiveData<List<EntryModel>> = entryRepository.entries

    fun insert(entryModel: EntryModel) = entryRepository.insert(entryModel)

    fun update(entryModel: EntryModel) = entryRepository.update(entryModel)

    fun delete(entryModel: EntryModel) = entryRepository.delete(entryModel)

}
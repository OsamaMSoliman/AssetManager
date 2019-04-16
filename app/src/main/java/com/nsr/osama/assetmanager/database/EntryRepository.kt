package com.nsr.osama.assetmanager.database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData


class EntryRepository(app: Application) {
    private val entryDao: EntryDao = EntryDB.getInstance(app).entryDao()
    val entries: LiveData<List<EntryModel>> = entryDao.query()

    fun insert(entryModel: EntryModel) = EntryAsyncTask(entryDao).execute(Pair('i', entryModel))!!

    fun update(entryModel: EntryModel) = EntryAsyncTask(entryDao).execute(Pair('u', entryModel))!!

    fun delete(entryModel: EntryModel) = EntryAsyncTask(entryDao).execute(Pair('d', entryModel))!!

    private class EntryAsyncTask(private val entryDao: EntryDao) : AsyncTask<Pair<Char, EntryModel>, Void, Void>() {
        override fun doInBackground(vararg params: Pair<Char, EntryModel>): Void? {
            when (params[0].first) {
                'i' -> entryDao.insert(params[0].second)
                'u' -> entryDao.update(params[0].second)
                'd' -> entryDao.delete(params[0].second)
            }
            return null
        }
    }

}
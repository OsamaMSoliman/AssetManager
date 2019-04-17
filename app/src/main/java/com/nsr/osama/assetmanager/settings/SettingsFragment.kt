package com.nsr.osama.assetmanager.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.nsr.osama.assetmanager.R


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)
    }
}
package com.nsr.osama.assetmanager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    // getItem is called to instantiate the fragment for the given page.
    override fun getItem(position: Int): Fragment = PlaceholderFragment.newInstance(position)

    override fun getCount(): Int = MainActivity.categoryCount
}
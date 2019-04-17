package com.nsr.osama.assetmanager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 * NOTE: This will keep every loaded fragment in memory.
 * If this becomes too memory intensive, it may be best to switch to a
 * androidx.fragment.app.FragmentStatePagerAdapter.
 */
class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm), ViewPager.OnPageChangeListener {

    var currentPos = 0

    // getItem is called to instantiate the fragment for the given page.
    override fun getItem(position: Int): Fragment = PlaceholderFragment.newInstance(position.toByte())

    override fun getCount(): Int = MainActivity.categoryCount.toInt()

    override fun onPageScrollStateChanged(state: Int) = Unit

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

    override fun onPageSelected(position: Int) {
        currentPos = position
    }
}
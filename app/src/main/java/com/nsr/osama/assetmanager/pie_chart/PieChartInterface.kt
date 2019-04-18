package com.nsr.osama.assetmanager.pie_chart

import com.nsr.osama.assetmanager.database.EntryModel

interface PieChartInterface {
    fun updatePieChart(filteredList: List<EntryModel>, withAnimation:Boolean)
}
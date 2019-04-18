package com.nsr.osama.assetmanager.pie_chart

import android.graphics.Color
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.nsr.osama.assetmanager.database.EntryModel

class PieChartListener(private val pieChart: PieChart, private val collapsingToolbarLayout: CollapsingToolbarLayout) : PieChartInterface {
    override fun updatePieChart(filteredList: List<EntryModel>, withAnimation: Boolean) {
        pieChart.data = PieData(PieDataSet(ArrayList<PieEntry>().apply {
            var ins = 0.0
            var outs = 0.0
            filteredList.forEach { e ->
                if (e.isIncrease) ins += e.price
                else outs += e.price
            }
            add(PieEntry(ins.toFloat(), "ins"))
            add(PieEntry(outs.toFloat(), "outs"))
            val diff = ins - outs
            collapsingToolbarLayout.title = diff.toString()
            collapsingToolbarLayout.setBackgroundColor(if (diff > 0) Color.GREEN else Color.RED)
        }, null).apply {
            sliceSpace = 2f
            colors = listOf(Color.GREEN, Color.RED)
        }).apply {
            setValueTextSize(24f)
            setValueTextColor(Color.BLACK)
        }
        if (withAnimation) {
            pieChart.animateXY(1000, 1000, Easing.EaseInOutQuad)
        } else {
            pieChart.notifyDataSetChanged()
            pieChart.invalidate()
        }
    }
}
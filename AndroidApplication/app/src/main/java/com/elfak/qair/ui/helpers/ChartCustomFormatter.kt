package com.elfak.qair.ui.helpers

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class ChartCustomFormatter : IndexAxisValueFormatter(){
    override fun getFormattedValue(value: Float): String {
        val dateInMillis = value.toLong()
        val date = Calendar.getInstance().apply {
            timeInMillis = dateInMillis
        }.time

        return SimpleDateFormat("dd/MM", Locale.getDefault()).format(date)
    }

}
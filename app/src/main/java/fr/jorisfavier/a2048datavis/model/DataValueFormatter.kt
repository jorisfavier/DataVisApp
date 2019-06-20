package fr.jorisfavier.a2048datavis.model

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

class DataValueFormatter(val labels: List<String>): ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return labels.getOrNull(value.toInt()) ?: value.toString()
    }
}
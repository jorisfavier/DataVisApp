package fr.jorisfavier.a2048datavis.utils

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import fr.jorisfavier.a2048datavis.model.DataObject
import fr.jorisfavier.a2048datavis.model.DataValueFormatter
import java.text.SimpleDateFormat
import java.util.*

fun Date.year(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.YEAR)
}

fun Date.month(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.MONTH)
}

fun Date.dayOfMonth(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_MONTH)
}

/**
 * Returns a String version of the date
 * @return a String based on the pattern MMM d, yyyy
 */
fun Date.formatStandard(): String {
    val formatter = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
    return formatter.format(this)
}

/**
 * Returns a String version of the date based on the AppAnnie server date pattern
 * @return a String following the yyyy-MM-dd pattern
 */
fun Date.formatForApi(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(this)
}

/**
 * Configure a pie chart with a dataObject's list
 */
fun PieChart.configureWith(dataObjects: List<DataObject>) {
    val entries = dataObjects.map { dataObject ->
        PieEntry(dataObject.value.toFloat(),dataObject.label)
    }
    val dataSet = PieDataSet(entries,"Download").apply {
        setColors(*ColorTemplate.COLORFUL_COLORS)
        setDrawValues(false)
    }

    description.isEnabled = false
    legend.isEnabled = false
    data = PieData(dataSet)
    setOnChartValueSelectedListener(object: OnChartValueSelectedListener {
        override fun onValueSelected(e: Entry?, h: Highlight?) {
            centerText = e?.let {
                it.y.toString()
            }
        }

        override fun onNothingSelected() {
            centerText = ""
        }
    })
}

/**
 * Configure a line chart with a dataObject's list
 */
fun LineChart.configureWith(dataObjects: List<DataObject>){
    val labels = ArrayList<String>()
    val entries = dataObjects.mapIndexed { index, dataObject ->
        labels.add(dataObject.label)
        Entry(index.toFloat(),dataObject.value.toFloat())
    }
    val dataSet = LineDataSet(entries,"Download")
    data = LineData(dataSet)
    description.isEnabled = false
    legend.isEnabled = false
    setDrawGridBackground(false)
    axisLeft.isEnabled = false
    xAxis.position = XAxis.XAxisPosition.BOTTOM
    xAxis.valueFormatter = DataValueFormatter(labels)
    xAxis.labelRotationAngle = -45f
    xAxis.textSize = 8f
    axisRight.granularity = 1f
}
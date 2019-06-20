package fr.jorisfavier.a2048datavis.ui.common

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import fr.jorisfavier.a2048datavis.model.DataFilterEnum
import fr.jorisfavier.a2048datavis.model.DataObject
import fr.jorisfavier.a2048datavis.utils.configureWith

abstract class SalesChartFragment: Fragment() {

    private var chart: ViewGroup? = null

    /**
     * Build a chart with the dataObject data and put it in the chartContainer view
     * The chart will be different according to the given filter
     * If a chart view already exist it will be removed and replaced by the new one
     * @param data : the data on which the chart will be based on
     * @param filter : will change the chart type
     * @param chartContainer : the view where the chart will be added
     */
    protected fun buildChart(data: List<DataObject>, filter: DataFilterEnum, chartContainer: ViewGroup){
        if(chart != null){
            chartContainer.removeView(chart)
        }
        if(filter == DataFilterEnum.DATE) {
            val lineChart = LineChart(context)
            lineChart.configureWith(data)
            chart = lineChart
            chartContainer.addView(chart)
            lineChart.invalidate()
        }
        else {
            val pieChart = PieChart(context)
            pieChart.configureWith(data)
            chart = pieChart
            chartContainer.addView(chart)
            pieChart.invalidate()
        }

        val layout = LinearLayout.LayoutParams(ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT))
        chart?.layoutParams = layout

    }
}
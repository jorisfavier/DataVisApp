package fr.jorisfavier.a2048datavis.ui.download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import fr.jorisfavier.a2048datavis.R
import fr.jorisfavier.a2048datavis.model.DataFilterEnum
import fr.jorisfavier.a2048datavis.model.DataObject
import fr.jorisfavier.a2048datavis.ui.home.MainViewModel
import fr.jorisfavier.a2048datavis.utils.configureWith
import kotlinx.android.synthetic.main.download_visualization_fragment.*


class DownloadVisualizationFragment : Fragment() {

    companion object {
        fun newInstance() = DownloadVisualizationFragment()
    }

    private lateinit var sharedViewModel: MainViewModel
    private var chart: ViewGroup? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.download_visualization_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            sharedViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
            initObserver()
        }
    }

    private fun initObserver(){
        sharedViewModel.getDownloadData().observe(this, Observer { dataList ->
            val total = dataList.map { it.value }.sum()
            download_total.text = getString(R.string.total_dl,total.toInt())
            buildChart(dataList)
        })
    }

    private fun buildChart(data: List<DataObject>){
        val filter = sharedViewModel.filter.value?: return
        if(chart != null){
            download_container.removeView(chart)
        }
        if(filter == DataFilterEnum.DATE) {
            val lineChart = LineChart(context)
            lineChart.configureWith(data)
            chart = lineChart
            download_container.addView(chart)
            lineChart.invalidate()
        }
        else {
            val pieChart = PieChart(context)
            pieChart.configureWith(data)
            chart = pieChart
            download_container.addView(chart)
            pieChart.invalidate()
        }

        val layout = LinearLayout.LayoutParams(ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT))
        chart?.layoutParams = layout

    }

}

package fr.jorisfavier.a2048datavis.ui.revenue

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import fr.jorisfavier.a2048datavis.R
import fr.jorisfavier.a2048datavis.ui.common.SalesChartFragment
import fr.jorisfavier.a2048datavis.ui.home.MainViewModel
import fr.jorisfavier.a2048datavis.utils.formatStandard
import kotlinx.android.synthetic.main.revenue_visualization_fragment.*

class RevenueVisualizationFragment : SalesChartFragment() {

    companion object {
        fun newInstance() = RevenueVisualizationFragment()
    }

    private lateinit var sharedViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.revenue_visualization_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            sharedViewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
            initObserver()
        }
    }

    private fun initObserver(){
        sharedViewModel.getRevenueData().observe(this, Observer { dataList ->
            val total = dataList.map { it.value }.sum()
            revenue_total.text = getString(R.string.total_revenue, total.formatStandard())
            sharedViewModel.filter.value?.let { filter ->
                buildChart(dataList,filter,revenue_container)
            }
        })
    }

}

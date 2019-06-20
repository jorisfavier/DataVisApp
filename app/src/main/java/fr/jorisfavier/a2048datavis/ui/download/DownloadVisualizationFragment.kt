package fr.jorisfavier.a2048datavis.ui.download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import fr.jorisfavier.a2048datavis.R
import fr.jorisfavier.a2048datavis.ui.common.SalesChartFragment
import fr.jorisfavier.a2048datavis.ui.home.MainViewModel
import fr.jorisfavier.a2048datavis.utils.formatStandard
import kotlinx.android.synthetic.main.download_visualization_fragment.*


class DownloadVisualizationFragment : SalesChartFragment() {

    companion object {
        fun newInstance() = DownloadVisualizationFragment()
    }

    private lateinit var sharedViewModel: MainViewModel

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
            download_total.text = getString(R.string.total_dl,total.formatStandard())
            sharedViewModel.filter.value?.let { filter ->
                buildChart(dataList,filter,download_container)
            }
        })
    }

}

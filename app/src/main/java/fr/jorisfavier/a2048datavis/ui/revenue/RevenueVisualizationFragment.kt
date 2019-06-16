package fr.jorisfavier.a2048datavis.ui.revenue

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.jorisfavier.a2048datavis.R

class RevenueVisualizationFragment : Fragment() {

    companion object {
        fun newInstance() = RevenueVisualizationFragment()
    }

    private lateinit var viewModel: RevenueVisualizationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.revenue_visualization_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RevenueVisualizationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

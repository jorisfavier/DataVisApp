package fr.jorisfavier.a2048datavis.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import fr.jorisfavier.a2048datavis.ui.download.DownloadVisualizationFragment
import fr.jorisfavier.a2048datavis.ui.revenue.RevenueVisualizationFragment

class MainPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                DownloadVisualizationFragment.newInstance()
            }
            else -> {
                RevenueVisualizationFragment.newInstance()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Downloads"
            else -> "Revenue"
        }
    }
}
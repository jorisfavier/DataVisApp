package fr.jorisfavier.a2048datavis.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.jorisfavier.a2048datavis.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pagerAdapter = MainPagerAdapter(supportFragmentManager)
        main_viewpager.adapter = pagerAdapter
        main_tablayout.setupWithViewPager(main_viewpager)
    }
}

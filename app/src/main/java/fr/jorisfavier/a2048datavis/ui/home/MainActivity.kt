package fr.jorisfavier.a2048datavis.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import fr.jorisfavier.a2048datavis.DataVisApp
import fr.jorisfavier.a2048datavis.R
import fr.jorisfavier.a2048datavis.api.AppAnnieService
import fr.jorisfavier.a2048datavis.databinding.ActivityMainBinding
import fr.jorisfavier.a2048datavis.utils.dayOfMonth
import fr.jorisfavier.a2048datavis.utils.month
import fr.jorisfavier.a2048datavis.utils.year
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var appAnnieService: AppAnnieService

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main)
        DataVisApp.currentInstance?.appModule?.inject(this)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.appAnnieService = appAnnieService
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        initPagerAdapter()
        initDatePickerButtons()
        viewModel.loadProductDates()
    }

    private fun initPagerAdapter() {
        val pagerAdapter = MainPagerAdapter(supportFragmentManager)
        main_viewpager.adapter = pagerAdapter
        main_tablayout.setupWithViewPager(main_viewpager)
    }

    private fun initDatePickerButtons(){
        main_startdate_button.setOnClickListener { view ->
            showDatePickerDialog(viewModel.selectedStartDate, viewModel.startDate, viewModel.endDate)
        }
        main_enddate_button.setOnClickListener { view ->
            showDatePickerDialog(viewModel.selectedEndDate, viewModel.startDate, viewModel.endDate)
        }
    }

    /**
     * Show a datepicker dialog initialized with the given date
     * then the date is updated with the user choice.
     * @param dateToSelect : the MutableLiveData which will be updated by the user
     * @param minDate : The minimal date shown by the calendar view
     * @param maxDate : The maximal date shown by the calendar view
     */
    private fun showDatePickerDialog(dateToSelect: MutableLiveData<Date>, minDate: Date?, maxDate: Date?){
        val date = dateToSelect.value
        if(date != null) {
            val datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { pickerView, chosenYear, chosenMonth, chosenDayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(chosenYear,chosenMonth,chosenDayOfMonth)
                    dateToSelect.value = calendar.time
                },
                date.year(),
                date.month(),
                date.dayOfMonth())
            minDate?.let {
                datePickerDialog.datePicker.minDate = it.time
            }
            maxDate?.let {
                datePickerDialog.datePicker.maxDate = it.time
            }
            datePickerDialog.show()
        }
    }
}

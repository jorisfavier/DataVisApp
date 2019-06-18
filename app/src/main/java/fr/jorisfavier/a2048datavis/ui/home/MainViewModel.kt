package fr.jorisfavier.a2048datavis.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import fr.jorisfavier.a2048datavis.utils.formatStandard
import java.util.*

class MainViewModel: ViewModel() {

    val selectedStartDate = MutableLiveData<Date>()
    val selectedEndDate = MutableLiveData<Date>()
    var startDate: Date? = null
    private set
    var endDate: Date? = null
    private set

    val startDateFormatted: LiveData<String> = Transformations.map(selectedStartDate) { it.formatStandard() }
    val endDateFormatted: LiveData<String> = Transformations.map(selectedEndDate) { it.formatStandard() }

    init {
        selectedStartDate.value = Date()
        selectedEndDate.value = Date()
    }

}
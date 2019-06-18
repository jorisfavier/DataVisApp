package fr.jorisfavier.a2048datavis.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import fr.jorisfavier.a2048datavis.api.AppAnnieService
import fr.jorisfavier.a2048datavis.model.dto.SharedAppDTO
import fr.jorisfavier.a2048datavis.utils.formatStandard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainViewModel: ViewModel() {

    lateinit var appAnnieService: AppAnnieService

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

    /**
     * Retrieve 2048 merge product informations and set max and min dates
     * Also set the selected start and end dates to cover a week based on the last date retrieved
     */
    fun loadProductDates(){
        appAnnieService.getProduct().enqueue(object: Callback<SharedAppDTO> {
            override fun onFailure(call: Call<SharedAppDTO>, t: Throwable) {
                Log.w(this.javaClass.simpleName, "Unable to retrieve sharings from AppAnnie service")
            }

            override fun onResponse(call: Call<SharedAppDTO>, response: Response<SharedAppDTO>) {
                response.body()?.let { sharedAppDTO ->
                    val sharedApp = sharedAppDTO.sharings.firstOrNull { it.ownerAccountId == AppAnnieService.accountId }
                    val product = sharedApp?.products?.firstOrNull { it.productId == AppAnnieService.productId }
                    if (product != null){
                        startDate = product.firstSalesDate
                        endDate = product.lastSalesDate
                        selectedEndDate.value = product.lastSalesDate
                        selectedStartDate.value = Date(product.lastSalesDate.time -(3600*24*7*1000))
                    }

                } ?: run {
                    Log.w(this.javaClass.simpleName, "Unable to retrieve 2048 merge product")
                }
            }
        })
    }

}
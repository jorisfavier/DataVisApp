package fr.jorisfavier.a2048datavis.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import fr.jorisfavier.a2048datavis.R
import fr.jorisfavier.a2048datavis.api.AppAnnieService
import fr.jorisfavier.a2048datavis.model.DataFilterEnum
import fr.jorisfavier.a2048datavis.model.DataObject
import fr.jorisfavier.a2048datavis.model.dto.SalesResponseDTO
import fr.jorisfavier.a2048datavis.model.dto.SharedAppDTO
import fr.jorisfavier.a2048datavis.utils.formatForApi
import fr.jorisfavier.a2048datavis.utils.formatStandard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel: ViewModel() {

    lateinit var appAnnieService: AppAnnieService

    val selectedStartDate = MutableLiveData<Date>()
    val selectedEndDate = MutableLiveData<Date>()
    val radioCheckedId = MutableLiveData<Int>()

    var startDate: Date? = null
    private set
    var endDate: Date? = null
    private set

    private val _downloadData = MutableLiveData<List<DataObject>>()
    private val _revenueData = MutableLiveData<List<DataObject>>()
    private val _error = MutableLiveData<String?>()

    val startDateFormatted: LiveData<String> = Transformations.map(selectedStartDate) { it.formatStandard() }
    val endDateFormatted: LiveData<String> = Transformations.map(selectedEndDate) { it.formatStandard() }

    val filter: LiveData<DataFilterEnum> = Transformations.map(radioCheckedId) { selectedRadioId ->
        if(selectedRadioId == R.id.main_date_choice) DataFilterEnum.DATE
        else DataFilterEnum.COUNTRY
    }

    fun getError(): LiveData<String?> = _error
    fun getDownloadData(): LiveData<List<DataObject>> = _downloadData
    fun getRevenueData(): LiveData<List<DataObject>> = _revenueData

    init {
        selectedStartDate.value = Date()
        selectedEndDate.value = Date()
        radioCheckedId.value = R.id.main_date_choice
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
                    if(sharedAppDTO.code == 200) {
                        val sharedApp = sharedAppDTO.sharings.firstOrNull { it.ownerAccountId == AppAnnieService.accountId }
                        val product = sharedApp?.products?.firstOrNull { it.productId == AppAnnieService.productId }
                        if (product != null){
                            startDate = product.firstSalesDate
                            endDate = product.lastSalesDate

                            //We set a start date 7 days earlier than the last sales date
                            selectedStartDate.value = Date(product.lastSalesDate.time -(3600*24*7*1000))
                            selectedEndDate.value = product.lastSalesDate
                        }
                    }

                } ?: run {
                    Log.w(this.javaClass.simpleName, "Unable to retrieve 2048 merge product")
                }
            }
        })
    }

    /**
     * Load sales data in the viewmodel based on the given filters
     * @param start : the start date filter
     * @param end : the end date filter
     * @param filter: the break down filter
     */
    fun loadSales(start: Date, end: Date, filter: DataFilterEnum){
        if(end.before(start)){
            _error.value = "Please select a start date prior to the end date."
            return
        }
        appAnnieService.getSales(start.formatForApi(),
            end.formatForApi(),
            filter.value)
            .enqueue(object: Callback<SalesResponseDTO> {
            override fun onFailure(call: Call<SalesResponseDTO>, t: Throwable) {
                _error.value = "Sorry we are unable to load the data, please check you internet connection."
            }

            override fun onResponse(call: Call<SalesResponseDTO>, response: Response<SalesResponseDTO>) {
                response.body()?.let { salesResponseDTO ->
                    if(salesResponseDTO.code == 200){
                        //By emiting a null value we indicate that there is no more error
                        _error.value = null
                        val dlData = ArrayList<DataObject>()
                        val revenueData = ArrayList<DataObject>()
                        salesResponseDTO.salesList.forEach { saleDto ->
                            val label = if (filter == DataFilterEnum.DATE) saleDto.date
                            else saleDto.country

                            dlData.add(DataObject(label,saleDto.units.product.downloads))
                            revenueData.add(DataObject(label,saleDto.revenue.iap.sales))
                        }
                        _downloadData.value = dlData
                        _revenueData.value = revenueData
                    }
                    else {
                        _error.value = "Sorry an error occured while retrieving your data"
                    }
                }
            }
        })
    }

}
package fr.jorisfavier.a2048datavis

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import fr.jorisfavier.a2048datavis.api.AppAnnieService
import fr.jorisfavier.a2048datavis.model.DataFilterEnum
import fr.jorisfavier.a2048datavis.model.DataObject
import fr.jorisfavier.a2048datavis.model.dto.*
import fr.jorisfavier.a2048datavis.ui.home.MainViewModel
import org.junit.Test

import org.junit.Rule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainViewModelUnitTest {
    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    private var appAnnieService: AppAnnieService = mock()

    /// Fake data
    private val validSale = SaleDTO("date",
        "NL",
        UnitDTO(SaleProductDTO(0.0,1.0,0.0,0.0)),
        RevenueDTO(IapDTO(0.0,2.0,0.0))
    )
    private var startDate = Date()
    //Set an end date 5 days later
    private var endDate= Date(startDate.time + 3600*24*5*1000)
    private var filter = DataFilterEnum.DATE
    private var salesDataDTO = SalesResponseDTO(listOf(validSale),
        null,
        200,
        null,
        1,
        0)


    @Test
    fun `loading sales data with internet connection should emit download and revenue data`() {
        //given
        val mockSuccessReponse = mock<Call<SalesResponseDTO>>()
        whenever(appAnnieService.getSales(any(), any(), any())) doReturn mockSuccessReponse
        doAnswer { invocationOnMock ->
            val callback = invocationOnMock.getArgument<Callback<SalesResponseDTO>>(0)
            callback.onResponse(mockSuccessReponse, Response.success(salesDataDTO))
            null
        }.`when`(mockSuccessReponse).enqueue(any())

        val viewmodel = MainViewModel()
        viewmodel.appAnnieService = appAnnieService


        val mockDownloadObserver : Observer<List<DataObject>> = mock()
        val mockRevenueObserver : Observer<List<DataObject>> = mock()
        viewmodel.getDownloadData().observeForever(mockDownloadObserver)
        viewmodel.getRevenueData().observeForever(mockRevenueObserver)

        val mockErrorObserver : Observer<String?> = mock()
        viewmodel.getError().observeForever(mockErrorObserver)

        //when
        viewmodel.loadSales(startDate, endDate, filter)

        //then
        verify(appAnnieService).getSales(any(), any(), any())
        verify(mockDownloadObserver).onChanged(check {
            assert(it.count() > 0)
        })
        verify(mockRevenueObserver).onChanged(check {
            assert(it.count() > 0)
        })
        verify(mockErrorObserver).onChanged(isNull())
    }

    @Test
    fun `loading sales data with end date prior to start date should emit error`() {
        //given
        val viewmodel = MainViewModel()
        viewmodel.appAnnieService = appAnnieService

        val mockDownloadObserver : Observer<List<DataObject>> = mock()
        val mockRevenueObserver : Observer<List<DataObject>> = mock()
        viewmodel.getDownloadData().observeForever(mockDownloadObserver)
        viewmodel.getRevenueData().observeForever(mockRevenueObserver)

        val mockErrorObserver : Observer<String?> = mock()
        viewmodel.getError().observeForever(mockErrorObserver)

        //when
        viewmodel.loadSales(endDate, startDate, filter)

        //then
        verify(appAnnieService, never()).getSales(any(), any(), any())
        verify(mockDownloadObserver, never()).onChanged(any())
        verify(mockRevenueObserver, never()).onChanged(any())
        verify(mockErrorObserver).onChanged(notNull())
    }

    @Test
    fun `loading sales data with server error should emit error`() {
        //given
        val viewmodel = MainViewModel()
        viewmodel.appAnnieService = appAnnieService
        salesDataDTO = SalesResponseDTO(listOf(),
            null,
            400,
            null,
            1,
            0)

        val mockSuccessResponse = mock<Call<SalesResponseDTO>>()
        whenever(appAnnieService.getSales(any(), any(), any())) doReturn mockSuccessResponse
        doAnswer { invocationOnMock ->
            val callback = invocationOnMock.getArgument<Callback<SalesResponseDTO>>(0)
            callback.onResponse(mockSuccessResponse, Response.success(salesDataDTO))
            null
        }.`when`(mockSuccessResponse).enqueue(any())

        val mockDownloadObserver : Observer<List<DataObject>> = mock()
        val mockRevenueObserver : Observer<List<DataObject>> = mock()
        viewmodel.getDownloadData().observeForever(mockDownloadObserver)
        viewmodel.getRevenueData().observeForever(mockRevenueObserver)

        val mockErrorObserver : Observer<String?> = mock()
        viewmodel.getError().observeForever(mockErrorObserver)

        //when
        viewmodel.loadSales(startDate, endDate, filter)

        //then
        verify(appAnnieService).getSales(any(), any(), any())
        verify(mockDownloadObserver, never()).onChanged(any())
        verify(mockRevenueObserver, never()).onChanged(any())
        verify(mockErrorObserver).onChanged(notNull())
    }

    @Test
    fun `loading sales data without internet should emit error`() {
        //given
        val mockFailResponse = mock<Call<SalesResponseDTO>>()
        whenever(appAnnieService.getSales(any(), any(), any())) doReturn mockFailResponse
        doAnswer { invocationOnMock ->
            val callback = invocationOnMock.getArgument<Callback<SalesResponseDTO>>(0)
            callback.onFailure(mockFailResponse,mock())
            null
        }.`when`(mockFailResponse).enqueue(any())


        val viewmodel = MainViewModel()
        viewmodel.appAnnieService = appAnnieService


        val mockDownloadObserver : Observer<List<DataObject>> = mock()
        val mockRevenueObserver : Observer<List<DataObject>> = mock()
        viewmodel.getDownloadData().observeForever(mockDownloadObserver)
        viewmodel.getRevenueData().observeForever(mockRevenueObserver)

        val mockErrorObserver : Observer<String?> = mock()
        viewmodel.getError().observeForever(mockErrorObserver)

        //when
        viewmodel.loadSales(startDate, endDate, filter)

        //then
        verify(appAnnieService).getSales(any(), any(), any())
        verify(mockDownloadObserver, never()).onChanged(any())
        verify(mockRevenueObserver, never()).onChanged(any())
        verify(mockErrorObserver).onChanged(notNull())
    }
}

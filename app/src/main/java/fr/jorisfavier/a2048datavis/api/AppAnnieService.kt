package fr.jorisfavier.a2048datavis.api

import fr.jorisfavier.a2048datavis.BuildConfig
import fr.jorisfavier.a2048datavis.model.dto.SalesResponseDTO
import fr.jorisfavier.a2048datavis.model.dto.SharedAppDTO
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*


interface AppAnnieService {
    @GET("sharing/products")
    fun getProduct() : Call<SharedAppDTO>

    @GET("accounts/${accountId}/products/${productId}/sales")
    fun getSales(@Query("start_date")
                 startDate: String,
                 @Query("end_date")
                 endDate: String,
                 @Query("break_down")
                 breakDown: String): Call<SalesResponseDTO>

    companion object {
        const val apiKey = BuildConfig.AppAnnieApiKey
        const val apiVersion = "1.3"
        const val accountId: Long = 250888
        const val productId: Long = 846723902
        const val baseUrl = "https://api.appannie.com/v${apiVersion}/"

        fun create(): AppAnnieService {
            //We create an interceptor to add the clien_id, client_secret et version to each
            //call for this API
            val interceptor = Interceptor { chain ->
                val requestBuilder = chain
                    .request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer ${apiKey}")

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(client)
                .build()

            return retrofit.create(AppAnnieService::class.java)
        }
    }
}
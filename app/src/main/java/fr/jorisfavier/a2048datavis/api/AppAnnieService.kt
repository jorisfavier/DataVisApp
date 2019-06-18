package fr.jorisfavier.a2048datavis.api

import fr.jorisfavier.a2048datavis.BuildConfig
import fr.jorisfavier.a2048datavis.model.dto.SharedAppDTO
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface AppAnnieService {
    @GET("sharing/products")
    fun getProduct() : Call<SharedAppDTO>

    companion object {
        val apiKey = BuildConfig.AppAnnieApiKey
        val apiVersion = "1.3"
        val accountId: Long = 250888
        val productId: Long = 846723902
        val baseUrl = "https://api.appannie.com/v${apiVersion}/"

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
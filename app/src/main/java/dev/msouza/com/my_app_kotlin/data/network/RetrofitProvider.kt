package dev.msouza.com.my_app_kotlin.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    val RetrofitApi: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    inline fun <reified T> get() : T = RetrofitApi.create(T::class.java)
}

object HttpClientProvider {

    val client: OkHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(OkHtt)

}
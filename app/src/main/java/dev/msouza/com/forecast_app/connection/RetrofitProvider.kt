package dev.msouza.com.forecast_app.connection

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dev.msouza.com.forecast_app.data.network.ConnectivityInterceptor
import dev.msouza.com.forecast_app.data.network.ConnectivityInterceptorImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val API_KEY = "080ccb2ad8b14329812153829192601"

object RetrofitProvider {

    val RetrofitApi: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl("https://api.apixu.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(HttpClientProvider.client)
                .build()
    }

    inline fun<reified T> get() : T {
        return RetrofitApi.create(T::class.java)
    }
}

object HttpClientProvider {

    val client: OkHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(AuthInterceptor())
            .build()
}

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("key", API_KEY)
                .build()

        val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

        return chain.proceed(request)
    }

}
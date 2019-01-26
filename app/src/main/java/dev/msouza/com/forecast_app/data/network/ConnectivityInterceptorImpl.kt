package dev.msouza.com.forecast_app.data.network

import android.content.Context
import android.net.ConnectivityManager
import dev.msouza.com.forecast_app.intern.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl (
        context: Context
) : ConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!hasInternetConnection())
            throw NoConnectivityException("the device does not have an internet connection")
        return chain.proceed(chain.request())
    }

    fun hasInternetConnection() : Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}
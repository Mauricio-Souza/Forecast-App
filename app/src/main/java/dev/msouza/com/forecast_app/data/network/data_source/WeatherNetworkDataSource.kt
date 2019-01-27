package dev.msouza.com.forecast_app.data.network.data_source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import dev.msouza.com.forecast_app.data.network.response.CurrentWeatherResponse
import dev.msouza.com.forecast_app.data.network.service.WeatherApiService
import dev.msouza.com.forecast_app.intern.NoConnectivityException

class WeatherNetworkDataSource (
        private val weatherApiService: WeatherApiService
) : WeatherDataSource {

    private val _noConnectivity = MediatorLiveData<String>()
    private val _downloadedCurrentWeather = MediatorLiveData<CurrentWeatherResponse>()

    override val noConnectivity: LiveData<String>
        get() = _noConnectivity

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val currentWeather = weatherApiService.getCurrentWeather(location, languageCode).await()
            _downloadedCurrentWeather.postValue(currentWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Error: ", e.message)
            _noConnectivity.postValue(e.message)
        }
    }
}
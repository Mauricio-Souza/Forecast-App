package dev.msouza.com.forecast_app.data.network.data_source

import androidx.lifecycle.LiveData
import dev.msouza.com.forecast_app.data.network.response.CurrentWeatherResponse

interface WeatherDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val noConnectivity: LiveData<String>

    suspend fun fetchCurrentWeather(location: String, languageCode: String)
}
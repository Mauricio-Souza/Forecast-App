package dev.msouza.com.forecast_app.data.provider

import dev.msouza.com.forecast_app.data.database.entities.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationString(): String
}
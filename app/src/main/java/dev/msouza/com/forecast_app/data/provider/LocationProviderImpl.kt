package dev.msouza.com.forecast_app.data.provider

import dev.msouza.com.forecast_app.data.database.entities.WeatherLocation

class LocationProviderImpl : LocationProvider {

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPreferredLocationString(): String {
        return "Sao Paulo"
    }
}
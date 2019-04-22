package dev.msouza.com.forecast_app.data.network.response

import dev.msouza.com.forecast_app.data.database.entities.CurrentWeatherEntry
import dev.msouza.com.forecast_app.data.database.entities.WeatherLocation

data class CurrentWeatherResponse (
        val location: WeatherLocation,
        val current: CurrentWeatherEntry
)
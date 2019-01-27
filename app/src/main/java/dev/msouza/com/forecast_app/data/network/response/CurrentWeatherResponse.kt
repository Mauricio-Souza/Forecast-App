package dev.msouza.com.forecast_app.data.network.response

import dev.msouza.com.forecast_app.data.database.entities.CurrentWeatherEntry
import dev.msouza.com.forecast_app.data.database.entities.Location

data class CurrentWeatherResponse (
        val location: Location,
        val current: CurrentWeatherEntry
)
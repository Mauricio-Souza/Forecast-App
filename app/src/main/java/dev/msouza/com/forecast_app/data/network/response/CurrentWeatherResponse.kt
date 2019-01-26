package dev.msouza.com.forecast_app.data.network.response

data class CurrentWeatherResponse (
        val location: Location,
        val current: CurrentWeatherEntry
)
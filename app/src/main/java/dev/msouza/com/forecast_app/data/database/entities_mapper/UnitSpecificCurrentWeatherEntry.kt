package dev.msouza.com.forecast_app.data.database.entities_mapper

interface UnitSpecificCurrentWeatherEntry {
    val temperature: Double
    val conditionText: String
    val conditionIconUrl: String
    val windSpeed: Double
    val windDirection: String
    val preciptationVolume: Double
    val feelsLikeTemperature: Double
    val visibilityDistance: Double
}
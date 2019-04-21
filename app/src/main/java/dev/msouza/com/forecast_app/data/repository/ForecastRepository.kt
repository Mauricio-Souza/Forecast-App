package dev.msouza.com.forecast_app.data.repository

import androidx.lifecycle.LiveData
import dev.msouza.com.forecast_app.data.database.entities.WeatherLocation
import dev.msouza.com.forecast_app.data.database.entities_mapper.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean) : LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation() : LiveData<WeatherLocation>
}
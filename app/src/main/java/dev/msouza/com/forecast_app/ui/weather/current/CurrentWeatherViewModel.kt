package dev.msouza.com.forecast_app.ui.weather.current

import androidx.lifecycle.ViewModel
import dev.msouza.com.forecast_app.data.provider.UnitProvider
import dev.msouza.com.forecast_app.data.provider.UnitProviderImpl
import dev.msouza.com.forecast_app.data.repository.ForecastRepository
import dev.msouza.com.forecast_app.intern.UnitSystem
import dev.msouza.com.forecast_app.intern.lazyDeferred

class CurrentWeatherViewModel(
        private val forecastRepository: ForecastRepository,
        unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }

    fun getUnitAbbreviation(metric: String, imperial: String) : String {
        return if (isMetric) metric else imperial
    }

}
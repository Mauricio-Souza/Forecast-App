package dev.msouza.com.forecast_app.ui.weather.current

import androidx.lifecycle.ViewModel
import dev.msouza.com.forecast_app.data.repository.ForecastRepository
import dev.msouza.com.forecast_app.intern.UnitSystem
import dev.msouza.com.forecast_app.intern.lazyDeferred

class CurrentWeatherViewModel(
        private val forecastRepository: ForecastRepository
) : ViewModel() {

    private val unitSystem = UnitSystem.METRIC

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }

    fun getUnitAbbreviation(metric: String, imperial: String) : String {
        return if (isMetric) metric else imperial
    }

}
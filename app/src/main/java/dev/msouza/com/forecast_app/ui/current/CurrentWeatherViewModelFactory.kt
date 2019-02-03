package dev.msouza.com.forecast_app.ui.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.msouza.com.forecast_app.data.repository.ForecastRepository

class CurrentWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(forecastRepository) as T
    }
}
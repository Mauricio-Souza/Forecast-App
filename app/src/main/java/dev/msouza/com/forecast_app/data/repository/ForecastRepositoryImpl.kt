package dev.msouza.com.forecast_app.data.repository

import androidx.lifecycle.LiveData
import dev.msouza.com.forecast_app.data.database.dao.CurrentWeatherDao
import dev.msouza.com.forecast_app.data.database.entities_mapper.UnitSpecificCurrentWeatherEntry
import dev.msouza.com.forecast_app.data.network.data_source.WeatherDataSource
import dev.msouza.com.forecast_app.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl (
        private val currentWeatherDao: CurrentWeatherDao,
        private val dataSource: WeatherDataSource
) : ForecastRepository {

    init {
        dataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            return@withContext if (metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()
        }
    }

    private fun persistFetchedCurrentWeather(currentWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.insertOrUpdate(currentWeather.current)
        }
    }

    private suspend fun initWeatherData() {
        if (isFetchedCurrentWeatherNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() {
        dataSource.fetchCurrentWeather("Sao Paulo", Locale.getDefault().language)
    }

    private fun isFetchedCurrentWeatherNeeded(lastFetchTime: ZonedDateTime) : Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}
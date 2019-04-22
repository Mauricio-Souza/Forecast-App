package dev.msouza.com.forecast_app.data.repository

import androidx.lifecycle.LiveData
import dev.msouza.com.forecast_app.data.database.dao.CurrentWeatherDao
import dev.msouza.com.forecast_app.data.database.dao.WeatherLocationDao
import dev.msouza.com.forecast_app.data.database.entities.WeatherLocation
import dev.msouza.com.forecast_app.data.database.entities_mapper.UnitSpecificCurrentWeatherEntry
import dev.msouza.com.forecast_app.data.network.data_source.WeatherDataSource
import dev.msouza.com.forecast_app.data.network.response.CurrentWeatherResponse
import dev.msouza.com.forecast_app.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl (
        private val currentWeatherDao: CurrentWeatherDao,
        private val weatherLocationDao: WeatherLocationDao,
        private val dataSource: WeatherDataSource,
        private val locationProvider: LocationProvider
) : ForecastRepository {

    init {
        dataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDao.getLocation()
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()
        }
    }

    private fun persistFetchedCurrentWeather(currentWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.insertOrUpdate(currentWeather.current)
            weatherLocationDao.insertOrUpdate(currentWeather.location)
        }
    }

    private suspend fun initWeatherData() {
        val lastWeatherLocation = weatherLocationDao.getLocation().value
        if (lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather()
            return
        }

        if (isFetchedCurrentWeatherNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() {
        dataSource.fetchCurrentWeather(locationProvider.getPreferredLocationString(), Locale.getDefault().language)
    }

    private fun isFetchedCurrentWeatherNeeded(lastFetchTime: ZonedDateTime) : Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}
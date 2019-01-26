package dev.msouza.com.forecast_app.data.network.service

import dev.msouza.com.forecast_app.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("current.json")
    fun getCurrentWeather(
            @Query("q") location: String,
            @Query("lang") languageCode: String = "en"
    ) : Deferred<CurrentWeatherResponse>

}
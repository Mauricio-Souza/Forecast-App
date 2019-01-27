package dev.msouza.com.forecast_app.data.network.response

import com.google.gson.annotations.SerializedName

data class CurrentWeatherEntry(
        @SerializedName("temp_c")
        val tempC: Int,
        @SerializedName("temp_f")
        val tempF: Double,
        @SerializedName("is_day")
        val isDay: Int,
        val condition: Condition,
        @SerializedName("wind_mph")
        val windMph: Double,
        @SerializedName("wind_kph")
        val windKph: Double,
        @SerializedName("wind_dir")
        val windDir: String,
        @SerializedName("precip_mm")
        val precipMm: Double,
        @SerializedName("precip_in")
        val precipIn: Double,
        @SerializedName("feelslike_c")
        val feelslikeC: Double,
        @SerializedName("feelslike_f")
        val feelslikeF: Double,
        @SerializedName("vis_km")
        val visKm: Double,
        @SerializedName("vis_miles")
        val visMiles: Double
)
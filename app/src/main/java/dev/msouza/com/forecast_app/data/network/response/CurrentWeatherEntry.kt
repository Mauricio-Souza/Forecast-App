package dev.msouza.com.forecast_app.data.network.response

import com.google.gson.annotations.SerializedName

data class CurrentWeatherEntry (
        @SerializedName("last_update_epoch")
        val lastUpdateEpoch: Int,
        @SerializedName("last_update")
        val lastUpdate: String,
        @SerializedName("temp_c")
        val tempC: Int,
        @SerializedName("temp_f")
        val tempF: Double,
        @SerializedName("is_day")
        val isDay: Int,
        val condition: Condition
)
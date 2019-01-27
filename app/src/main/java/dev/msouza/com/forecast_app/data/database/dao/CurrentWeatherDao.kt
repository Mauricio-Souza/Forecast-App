package dev.msouza.com.forecast_app.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.msouza.com.forecast_app.data.database.entities.CURRENT_WEATHER_ID
import dev.msouza.com.forecast_app.data.database.unitlocalized.ImperialCurrentWeatherEntry
import dev.msouza.com.forecast_app.data.database.unitlocalized.MetricCurrentWeatherEntry
import dev.msouza.com.forecast_app.data.database.unitlocalized.UnitSpecificCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(currentWeatherEntry: UnitSpecificCurrentWeatherEntry)

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric() : LiveData<MetricCurrentWeatherEntry>

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial() : LiveData<ImperialCurrentWeatherEntry>
}
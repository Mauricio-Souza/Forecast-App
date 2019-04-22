package dev.msouza.com.forecast_app.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.msouza.com.forecast_app.data.database.entities.WEATHER_LOCATION_ID
import dev.msouza.com.forecast_app.data.database.entities.WeatherLocation

@Dao
interface WeatherLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(weatherLocation: WeatherLocation)

    @Query("select * from weather_location where id = $WEATHER_LOCATION_ID")
    fun getLocation() : LiveData<WeatherLocation>
}
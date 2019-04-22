package dev.msouza.com.forecast_app.data.database.db_provider

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.msouza.com.forecast_app.data.database.dao.CurrentWeatherDao
import dev.msouza.com.forecast_app.data.database.dao.WeatherLocationDao
import dev.msouza.com.forecast_app.data.database.entities.CurrentWeatherEntry
import dev.msouza.com.forecast_app.data.database.entities.WeatherLocation

const val DATABASE_NAME = "forecast.db"

@Database(entities = arrayOf(CurrentWeatherEntry::class, WeatherLocation::class), version = 1, exportSchema = false)
abstract class ForecastDatabase : RoomDatabase() {

    abstract fun weatherDao() : CurrentWeatherDao
    abstract fun weatherLocation() : WeatherLocationDao

    companion object {

        @Volatile private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) : ForecastDatabase {
            return Room.databaseBuilder(
                    context.applicationContext,
                    ForecastDatabase::class.java,
                    DATABASE_NAME
            ).build()
        }
    }
}
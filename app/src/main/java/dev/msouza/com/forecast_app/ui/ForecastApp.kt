package dev.msouza.com.forecast_app.ui

import android.app.Application
import dev.msouza.com.forecast_app.data.database.db_provider.ForecastDatabase
import dev.msouza.com.forecast_app.data.network.ConnectivityInterceptor
import dev.msouza.com.forecast_app.data.network.ConnectivityInterceptorImpl
import dev.msouza.com.forecast_app.data.network.client_provider.RetrofitProvider
import dev.msouza.com.forecast_app.data.network.data_source.WeatherDataSource
import dev.msouza.com.forecast_app.data.network.data_source.WeatherNetworkDataSource
import dev.msouza.com.forecast_app.data.repository.ForecastRepository
import dev.msouza.com.forecast_app.data.repository.ForecastRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ForecastApp : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApp))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().weatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { RetrofitProvider(instance()) }
        bind<WeatherDataSource>() with singleton { WeatherNetworkDataSource(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }
    }
}
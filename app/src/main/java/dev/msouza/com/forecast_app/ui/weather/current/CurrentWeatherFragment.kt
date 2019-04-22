package dev.msouza.com.forecast_app.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import dev.msouza.com.forecast_app.R
import dev.msouza.com.forecast_app.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()

    lateinit var viewModel: CurrentWeatherViewModel

    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {

        viewModel.weatherLocation.await().observe(this@CurrentWeatherFragment, Observer { location ->
            if (location == null) return@Observer

            setupToolbarTitle(location.name)
        })

        viewModel.weather.await().observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer

            groupLoading.visibility = View.GONE
            updateCondition(it.conditionText)
            updatePreciptation(it.preciptationVolume)
            updateTemperature(it.temperature, it.feelsLikeTemperature)
            updateWind(it.windDirection, it.windSpeed)
            updateVisibility(it.visibilityDistance)

            Glide.with(this@CurrentWeatherFragment)
                    .load("http:${it.conditionIconUrl}")
                    .into(ivConditionIcon)

        })
    }

    private fun setupToolbarTitle(location: String) {
        val parent = (activity as? AppCompatActivity)
        parent?.supportActionBar?.title = location
        parent?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperature(temperature: Double, feelsLike: Double) {
        val unitAbbreviation = viewModel.getUnitAbbreviation("ºC", "ºF")
        tvTemperature.text = "$temperature $unitAbbreviation"
        tvFeelsLikeTemperature.text = "Feels like $feelsLike $unitAbbreviation"
    }

    private fun updatePreciptation(preciptationVolume: Double) {
        val unitAbbreviation = viewModel.getUnitAbbreviation("mm", "in")
        tvPreciptation.text = "Preciptation: $preciptationVolume $unitAbbreviation"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        val unitAbbreviation = viewModel.getUnitAbbreviation("kph", "mph")
        tvWind.text = "Wind: $windDirection $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = viewModel.getUnitAbbreviation("km", "mi.")
        tvVisibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        tvCondition.text = condition
    }

}
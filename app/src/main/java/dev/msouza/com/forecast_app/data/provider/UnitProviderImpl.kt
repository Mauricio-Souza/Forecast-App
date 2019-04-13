package dev.msouza.com.forecast_app.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dev.msouza.com.forecast_app.R
import dev.msouza.com.forecast_app.intern.UnitSystem

class UnitProviderImpl(private val context: Context) : UnitProvider {

    private val appContext = context.applicationContext
    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(context.getString(R.string.key_list_units), UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)
    }
}
package dev.msouza.com.forecast_app.data.provider

import dev.msouza.com.forecast_app.intern.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}
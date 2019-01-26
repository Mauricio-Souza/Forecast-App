package dev.msouza.com.forecast_app.intern

import java.io.IOException

class NoConnectivityException(override var message: String) : IOException(message)
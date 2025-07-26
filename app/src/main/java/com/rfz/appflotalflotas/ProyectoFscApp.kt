package com.rfz.appflotalflotas

import android.app.Application
import com.rfz.appflotalflotas.core.util.AppLocale
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProyectoFscApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppLocale.loadSavedLocale(this)
    }
}
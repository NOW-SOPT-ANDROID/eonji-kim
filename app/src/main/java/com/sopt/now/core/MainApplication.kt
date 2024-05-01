package com.sopt.now.core

import android.app.Application
import com.sopt.now.data.PreferenceUtil
import timber.log.Timber

class MainApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()
        setTimber()
        initPreference()
    }

    private fun initPreference() {
        prefs = PreferenceUtil(applicationContext)
    }

    private fun setTimber() {
        Timber.plant(Timber.DebugTree())
    }
}
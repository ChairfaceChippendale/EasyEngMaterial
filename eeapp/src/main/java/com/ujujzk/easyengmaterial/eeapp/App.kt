package com.ujujzk.easyengmaterial.eeapp

import android.app.Application
import com.ujujzk.easyengmaterial.eeapp.koin.appModules
import com.ujujzk.easyengmaterial.eeapp.tools.Looog
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Looog.init()

        startKoin{
            androidContext(this@App)
            properties(mapOf(
                "debug" to BuildConfig.DEBUG.toString() //"debug" property let us know app build type in any module

            ))
            modules(appModules)
        }

    }
}
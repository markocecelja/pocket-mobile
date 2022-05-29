package com.mcecelja.pocket

import android.app.Application
import com.mcecelja.pocket.di.appModule
import com.mcecelja.pocket.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Pocket : Application() {

    companion object {
        lateinit var application: Pocket
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        startKoin {
            androidContext(this@Pocket)
            modules(appModule, viewModelModule)
        }
    }
}
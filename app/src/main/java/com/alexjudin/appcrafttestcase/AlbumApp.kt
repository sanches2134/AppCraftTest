package com.alexjudin.appcrafttestcase

import android.app.Application
import com.alexjudin.appcrafttestcase.di.component.AppComponent
import com.alexjudin.appcrafttestcase.di.component.DaggerAppComponent
import com.alexjudin.appcrafttestcase.di.module.ContextModule


class AlbumApp : Application() {
    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component =
        DaggerAppComponent.builder().contextModule(ContextModule(this.applicationContext))
                .build()
    }
}

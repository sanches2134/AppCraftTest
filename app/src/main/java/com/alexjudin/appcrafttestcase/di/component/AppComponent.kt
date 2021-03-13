package com.alexjudin.appcrafttestcase.di.component


import com.alexjudin.appcrafttestcase.di.module.ContextModule
import com.alexjudin.appcrafttestcase.di.module.DataModule
import com.alexjudin.appcrafttestcase.di.module.DomainModule
import com.alexjudin.appcrafttestcase.di.module.PresentationModule
import com.alexjudin.appcrafttestcase.ui.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, DataModule::class, DomainModule::class, PresentationModule::class])
interface AppComponent {

    fun injectMainActivity(mainActivity: MainActivity)

}
package com.test.propertymanagementapp.app

import android.app.Application
import com.test.propertymanagementapp.di.components.AppComponent
import com.test.propertymanagementapp.di.components.DaggerAppComponent
import com.test.propertymanagementapp.di.modules.AppModule
import com.test.propertymanagementapp.di.modules.ViewModelModule

class CustomApplication : Application() {

    lateinit var component :AppComponent
    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .appModule(AppModule())
            .viewModelModule(ViewModelModule(applicationContext))
            .build()
    }
}
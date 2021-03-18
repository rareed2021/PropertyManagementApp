package com.test.propertymanagementapp.di.modules

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.test.propertymanagementapp.app.ViewModelFactory
import com.test.propertymanagementapp.ui.auth.AuthViewModel
import dagger.Module
import dagger.Provides


@Module
class ActivityModule {
    @Provides
    fun provideViewModel(activity:AppCompatActivity, factory:ViewModelFactory):AuthViewModel{
        return ViewModelProvider(activity, factory).get(AuthViewModel::class.java)
    }
}
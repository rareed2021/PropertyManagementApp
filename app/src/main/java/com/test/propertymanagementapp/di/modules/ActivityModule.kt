package com.test.propertymanagementapp.di.modules

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.test.propertymanagementapp.app.ViewModelFactory
import com.test.propertymanagementapp.ui.auth.AuthViewModel
import com.test.propertymanagementapp.ui.home.HomeViewModel
import com.test.propertymanagementapp.ui.properties.PropertyViewModel
import com.test.propertymanagementapp.ui.todo.TodoViewModel
import dagger.Module
import dagger.Provides


@Module
class ActivityModule {
    @Provides
    fun provideAuthViewModel(activity:AppCompatActivity, factory:ViewModelFactory):AuthViewModel{
        return ViewModelProvider(activity, factory).get(AuthViewModel::class.java)
    }

    @Provides
    fun provideHomeViewModel(activity: AppCompatActivity, factory: ViewModelFactory):HomeViewModel{
        return ViewModelProvider(activity, factory).get(HomeViewModel::class.java)
    }
    @Provides
    fun providesPropertyViewModel(activity: AppCompatActivity, factory: ViewModelFactory):PropertyViewModel{
        return ViewModelProvider(activity,factory).get(PropertyViewModel::class.java)
    }

    @Provides
    fun providesTodoViewModel(activity: AppCompatActivity, factory: ViewModelFactory):TodoViewModel{
        return ViewModelProvider(activity,factory).get(TodoViewModel::class.java)
    }
}
package com.test.propertymanagementapp.di.components

import com.test.propertymanagementapp.di.annotations.ApplicationScope
import com.test.propertymanagementapp.di.modules.AppModule
import com.test.propertymanagementapp.di.modules.ViewModelModule
import com.test.propertymanagementapp.ui.auth.LoginActivity
import com.test.propertymanagementapp.ui.auth.RegisterFragment
import dagger.Component

@Component(modules = [AppModule::class, ViewModelModule::class])
@ApplicationScope
interface AppComponent {
    fun inject(activity:LoginActivity)
    fun inject(registerFragment: RegisterFragment)
}
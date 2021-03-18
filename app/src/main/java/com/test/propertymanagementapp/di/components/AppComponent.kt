package com.test.propertymanagementapp.di.components

import android.content.Context
import com.test.propertymanagementapp.di.annotations.ApplicationScope
import com.test.propertymanagementapp.di.modules.AppModule
import com.test.propertymanagementapp.di.modules.ViewModelModule
import com.test.propertymanagementapp.ui.auth.LoginActivity
import com.test.propertymanagementapp.ui.auth.RegisterFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
@ApplicationScope
interface AppComponent {

    val activityComponent : ActivityComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance activityContext:Context
        ) : AppComponent
    }
}
package com.test.propertymanagementapp.di.components


import androidx.appcompat.app.AppCompatActivity
import com.test.propertymanagementapp.di.annotations.ActivityScope
import com.test.propertymanagementapp.di.modules.ActivityModule
import com.test.propertymanagementapp.ui.auth.LoginActivity
import com.test.propertymanagementapp.ui.auth.RegisterFragment
import dagger.BindsInstance
import dagger.Subcomponent


@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: LoginActivity)
    fun inject(registerFragment: RegisterFragment)
    @Subcomponent.Factory
    interface Factory{
        fun create(
            @BindsInstance activity:AppCompatActivity
        ):ActivityComponent
    }
}
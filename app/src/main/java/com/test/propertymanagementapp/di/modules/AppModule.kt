package com.test.propertymanagementapp.di.modules

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.test.propertymanagementapp.di.annotations.ApplicationScope
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.test.propertymanagementapp.app.Config
import com.test.propertymanagementapp.app.ViewModelFactory
import com.test.propertymanagementapp.data.database.Database
import com.test.propertymanagementapp.data.database.UserDao
import com.test.propertymanagementapp.data.network.PropertyApi
import com.test.propertymanagementapp.data.repositories.AuthRepository
import com.test.propertymanagementapp.di.components.ActivityComponent
import com.test.propertymanagementapp.ui.auth.AuthValidator
import com.test.propertymanagementapp.ui.auth.AuthViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider


@Module(subcomponents = [ActivityComponent::class])
class AppModule() {

    @Provides
    @ApplicationScope
    fun providesGsonConverter() = GsonConverterFactory.create()

    @Provides
    @ApplicationScope
    fun providesRxAdapter() =RxJava2CallAdapterFactory.create()

    @Provides
    @ApplicationScope
    fun providesRetrofit(gson:GsonConverterFactory, rxAdapter:RxJava2CallAdapterFactory)=Retrofit.Builder()
        .baseUrl(Config.BASE_URL)
        .addConverterFactory(gson)
        .addCallAdapterFactory(rxAdapter)
        .build()

    @Provides
    @ApplicationScope
    fun providesPropertyApi(retrofit: Retrofit):PropertyApi{
        return retrofit.create(PropertyApi::class.java)
    }

    @Provides
    @ApplicationScope
    fun providesDatabase(context:Context):Database{
        return Room.databaseBuilder(context, Database::class.java,Config.DATABASE_NAME)
            .build()
    }

    @Provides
    fun providesUserDao(db:Database):UserDao{
        return db.getUserDao()
    }

    @Provides
    fun provideSharedPreferences(context:Context)=context
        .getSharedPreferences(Config.SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)

    @Provides
    @IntoMap
    @ClassKey(AuthViewModel::class)
    fun providesAuthViewModel(repository: AuthRepository, validator: AuthValidator) : ViewModel{
        return AuthViewModel(repository, validator)
    }

    @Provides
    @JvmSuppressWildcards
    fun provideViewModelFactory(providers:Map<Class<*>, Provider<ViewModel>>): ViewModelFactory {
        return ViewModelFactory(providers)
    }



}
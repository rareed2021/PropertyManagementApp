package com.test.propertymanagementapp.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.test.propertymanagementapp.di.annotations.ApplicationScope
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.test.propertymanagementapp.app.Config
import com.test.propertymanagementapp.data.network.PropertyApi
import com.test.propertymanagementapp.ui.auth.AuthValidator
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
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

//    @Provides
//    @ApplicationScope
//    fun providesSharedPreferences() : SharedPreferences{
//        return applicationContext.getSharedPreferences(Config.SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
//    }


}
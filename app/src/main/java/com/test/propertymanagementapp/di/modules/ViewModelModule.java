package com.test.propertymanagementapp.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;

import com.test.propertymanagementapp.app.Config;
import com.test.propertymanagementapp.app.ViewModelFactory;
import com.test.propertymanagementapp.data.network.PropertyApi;
import com.test.propertymanagementapp.data.repositories.AuthLocalDataSource;
import com.test.propertymanagementapp.data.repositories.AuthRemoteDataSource;
import com.test.propertymanagementapp.data.repositories.AuthRepository;
import com.test.propertymanagementapp.ui.auth.AuthValidator;
import com.test.propertymanagementapp.ui.auth.AuthViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import javax.inject.Provider;

import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public class ViewModelModule {

    Context applicationContext;
    public ViewModelModule(Context context){
        applicationContext = context;
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @Provides
    ViewModelFactory viewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }

    @Provides
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    ViewModel viewModelAuth(AuthRepository repository, AuthValidator validator) {
        return new AuthViewModel(repository, validator);
    }

    @Provides
    SharedPreferences providesSharedPreferences(){
        return applicationContext.getSharedPreferences(Config.SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE);
    }


//    @Provides
//    AuthRemoteDataSource providesAuthRemoteData(PropertyApi api) {return new AuthRemoteDataSource(api);}
//
//    @Provides
//    AuthLocalDataSource providesAuthLocalData(SharedPreferences sharedPreferences){
//        return new AuthLocalDataSource(sharedPreferences);
//    }
//
//    @Provides
//    AuthRepository providesAuthRepository(AuthRemoteDataSource remoteData,
//                                          AuthLocalDataSource localData){
//        return new AuthRepository(remoteData, localData);
//    }

//    @Provides
//    AuthValidator providesAuthValidator(){return new AuthValidator();}


}
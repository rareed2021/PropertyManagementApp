package com.test.propertymanagementapp.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;

import com.test.propertymanagementapp.app.Config;
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


    public ViewModelModule(){}

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

//    @Provides
//    ViewModelFactoryJava viewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
//        return new ViewModelFactoryJava(providerMap);
//    }

    @Provides
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    ViewModel viewModelAuth(AuthRepository repository, AuthValidator validator) {
        return new AuthViewModel(repository, validator);
    }

    @Provides
    SharedPreferences providesSharedPreferences(Context context){
        return context.getSharedPreferences(Config.SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE);
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
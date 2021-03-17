package com.test.propertymanagementapp.data.repositories

import android.content.SharedPreferences
import android.util.Log
import com.test.propertymanagementapp.app.Config
import com.test.propertymanagementapp.data.database.UserDao
import com.test.propertymanagementapp.data.models.AuthResponse
import com.test.propertymanagementapp.data.models.RegistrationUser
import com.test.propertymanagementapp.data.models.User
import com.test.propertymanagementapp.data.network.PropertyApi
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepository @Inject constructor(val remoteData:AuthRemoteDataSource, val localData: AuthLocalDataSource) {
    fun login(email:String,password:String) : Single<AuthResponse> = remoteData.login(email, password)
        .doOnSuccess {  }
            .map { handleResult(it,localData::login)}

    fun register(user: RegistrationUser) = remoteData.register(user)
            .map { handleResult(it,localData::register) }

    val user:User?=localData.currentUser


    companion object{
        private fun handleResult(response:AuthResponse, successCallback: (User)->Unit):AuthResponse{
            Log.d("myapp","Handling result")
            if(!response.error){
                Log.d("myapp","Result is not error")
                Thread {
                    kotlin.run {
                        Observable.interval(30,TimeUnit.SECONDS)
                        val user = response.user
                        if (user != null)
                            successCallback(user)
                    }
                }.start()
            }
            return response
        }
    }
    inner class AuthResponseObserver(val successCallback: (User)->Unit):SingleObserver<AuthResponse>{
        override fun onSubscribe(d: Disposable) {
        }

        override fun onSuccess(t: AuthResponse) {
            if(! t.error){
                val user = t.user
                if(user!=null) {
                    successCallback(user)
                }
            }
        }

        override fun onError(e: Throwable) {
            Log.d("myapp", "Local error: ${e.localizedMessage}")
        }

    }
}

class AuthRemoteDataSource @Inject constructor(private val api:PropertyApi) {
    fun login(email:String,password:String):Single<AuthResponse>{
        return api.login(RegistrationUser(email=email,password = password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun register(user: RegistrationUser) = api.register(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}


class AuthLocalDataSource @Inject constructor(private val prefs: SharedPreferences,
                                              private val userDao:UserDao
){
    fun login(user:User){
            Log.d("myapp", "Logged in: ${user._id}")
            prefs.edit().putString(Config.CURRENT_USER_KEY, user._id).apply()
            userDao.addUser(user)
    }
    fun register(user:User){
        prefs.edit().putString(Config.CURRENT_USER_KEY,user._id).apply()
        userDao.addUser(user)
    }
    val currentUser:User?
        get(){
            val uid = prefs.getString(Config.CURRENT_USER_KEY, null)
            return uid?.let {
                userDao.getUser(uid)
            }
        }
}
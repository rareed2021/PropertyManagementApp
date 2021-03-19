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
import kotlinx.coroutines.currentCoroutineContext
import org.reactivestreams.Subscriber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remoteData: AuthRemoteDataSource,
    private val localData: AuthLocalDataSource
) {
    suspend fun login(email: String, password: String): AuthResponse {
        return remoteData.login(email, password).also {
            it.user?.also {u-> localData.login(u)}
        }
    }

    //  .doOnSuccess { handleResult(it, localData::login) }

    suspend fun register(user: RegistrationUser) = remoteData.register(user).also {
        it.user?.also { u->localData.register(u) }
    }

    suspend fun logout()= localData.logout()
    //.doOnSuccess { handleResult(it, localData::register) }


    companion object {
        private suspend fun handleResult(
            response: AuthResponse,
            successCallback: suspend (User) -> Unit
        ) {
            Log.d("myapp", "Handling result")
            if (!response.error) {
                Log.d("myapp", "Result is not error")
                val user = response.user
                if (user != null)
                    successCallback(user)
            }
        }
    }

    suspend fun  getUser():User?{
        Log.d("myapp","Getting user")
        return localData.getUser()
    }
}

//inner class AuthResponseObserver(val successCallback: (User) -> Unit) :
//    SingleObserver<AuthResponse> {
//    override fun onSubscribe(d: Disposable) {
//    }
//
//    override fun onSuccess(t: AuthResponse) {
//        if (!t.error) {
//            val user = t.user
//            if (user != null) {
//                successCallback(user)
//            }
//        }
//    }
//
//    override fun onError(e: Throwable) {
//        Log.d("myapp", "Local error: ${e.localizedMessage}")
//    }
//
//}


class AuthRemoteDataSource @Inject constructor(private val api: PropertyApi) {
    suspend fun login(email: String, password: String): AuthResponse {
        return api.login(RegistrationUser(email = email, password = password))
    }

    suspend fun register(user: RegistrationUser) = api.register(user)


}


class AuthLocalDataSource @Inject constructor(
    private val prefs: SharedPreferences,
    private val userDao: UserDao
) {
    suspend fun login(user: User) {
        Log.d("myapp", "Logged in: ${user._id}")
        prefs.edit().putString(Config.CURRENT_USER_KEY, user._id).apply()
        userDao.addUser(user)
    }

    suspend fun register(user: User) {
        prefs.edit().putString(Config.CURRENT_USER_KEY, user._id).apply()
        userDao.addUser(user)
    }

    suspend fun getUser() : User?{
        val uid = prefs.getString(Config.CURRENT_USER_KEY, null)
        Log.d("myapp","$uid")
        return uid?.let {
            userDao.getUser(uid)
        }
    }

    fun logout() {
        prefs.edit().remove(Config.CURRENT_USER_KEY).apply()
    }
}
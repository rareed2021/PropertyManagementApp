package com.test.propertymanagementapp.data.repositories

import android.content.SharedPreferences
import android.util.Log
import com.test.propertymanagementapp.app.Config
import com.test.propertymanagementapp.data.database.UserDao
import com.test.propertymanagementapp.data.models.User
import javax.inject.Inject

class HomeRepository @Inject constructor(private val localData:HomeLocalData, private val remoteData:HomeRemoteData) {
    suspend fun  getUser():User?{
        Log.d("myapp","Getting user")
        return localData.getUser()
    }
}

class HomeRemoteData @Inject constructor(){

}


class HomeLocalData @Inject constructor(val userDao: UserDao, val prefs:SharedPreferences){

    suspend fun getUser() : User?{
        val uid = prefs.getString(Config.CURRENT_USER_KEY, null)
        Log.d("myapp","$uid")
        return uid?.let {
            userDao.getUser(uid)
        }
    }
}
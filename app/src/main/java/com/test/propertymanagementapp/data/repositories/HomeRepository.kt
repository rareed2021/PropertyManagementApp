package com.test.propertymanagementapp.data.repositories

import android.content.SharedPreferences
import com.test.propertymanagementapp.app.Config
import com.test.propertymanagementapp.data.database.UserDao
import com.test.propertymanagementapp.data.models.User
import javax.inject.Inject

class HomeRepository @Inject constructor(private val localData:HomeLocalData, private val remoteData:HomeRemoteData) {
    val user: User?
        get() = localData.currentUser
}

class HomeRemoteData @Inject constructor(){

}


class HomeLocalData @Inject constructor(val userDao: UserDao, val prefs:SharedPreferences){

    val currentUser: User?
        get() {
            val uid = prefs.getString(Config.CURRENT_USER_KEY, null)
            return uid?.let {
                userDao.getUser(uid)
            }
        }
}
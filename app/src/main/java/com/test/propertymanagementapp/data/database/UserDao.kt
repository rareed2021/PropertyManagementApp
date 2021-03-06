package com.test.propertymanagementapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.propertymanagementapp.data.models.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User):Long

    @Query("select * from User where _id = :id")
    suspend fun getUser(id:String):User?

    @Query("update user set name =ifnull(:name,name), email= ifnull(:email,email), password=ifnull(:password,password), landlordEmail= ifnull(:landlordEmail,landlordEmail) where _id=:id")
    suspend fun updateUserInfo(id: String, name:String?=null,email:String?=null, landlordEmail:String?=null,password:String?=null):Int
}
package com.test.propertymanagementapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.propertymanagementapp.data.models.Property
import com.test.propertymanagementapp.data.models.User


@Database(entities=[User::class, Property::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class Database :RoomDatabase(){
    abstract fun getUserDao():UserDao
    abstract fun getPropertyDao():PropertyDao
}
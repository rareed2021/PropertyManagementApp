package com.test.propertymanagementapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.propertymanagementapp.data.models.Property
import com.test.propertymanagementapp.data.models.PropertyEntity

@Dao
interface PropertyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProperty(property:PropertyEntity):Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProperty(properties:List<PropertyEntity>)


    @Query("select * from Property where id=:propertyId")
    suspend fun getProperty(propertyId:String): PropertyEntity?

    @Query("select * from Property where userId=:userId")
    suspend fun listProperties(userId:String):List<PropertyEntity>

    @Query("select * from Property where userId = :userId")
    fun watchProperties(userId:String): LiveData<List<PropertyEntity>>

    @Update
    suspend fun updateProperty(property: PropertyEntity):Int

}
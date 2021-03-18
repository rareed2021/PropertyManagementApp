package com.test.propertymanagementapp.data.database

import androidx.room.*
import com.test.propertymanagementapp.data.models.Property

@Dao
interface PropertyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProperty(property:Property):Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProperty(properties:List<Property>)


    @Query("select * from Property where id=:propertyId")
    suspend fun getProperty(propertyId:String):Property?

    @Query("select * from Property where userId=:userId")
    suspend fun listProperties(userId:String):List<Property>

    @Update
    suspend fun updateProperty(property: Property):Int

}
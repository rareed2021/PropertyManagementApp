package com.test.propertymanagementapp.data.repositories

import com.test.propertymanagementapp.data.database.PropertyDao
import com.test.propertymanagementapp.data.models.Property
import com.test.propertymanagementapp.data.network.PropertyApi
import javax.inject.Inject

class PropertyRepository @Inject constructor(val localData:PropertyLocalDataSource, val remoteData:PropertyRemoteDataSource) {
    suspend fun getProperties(uid:String):List<Property>{
        val properties = remoteData.getProperties(uid)
        properties?.also{
            localData.addProperties(it)
        }
        return localData.getProperties(uid)
    }
}

class PropertyRemoteDataSource @Inject constructor(val api:PropertyApi){
    suspend fun getProperties(uid:String)=api.getProperties(uid).data
}

class PropertyLocalDataSource @Inject constructor(val dao:PropertyDao){
    suspend fun addProperties(properties:List<Property>){
        dao.addProperty(properties)
    }

    suspend fun getProperties(uid: String) :List<Property>{
        return dao.listProperties(uid)
    }
}

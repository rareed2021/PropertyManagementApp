package com.test.propertymanagementapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
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
    suspend fun addProperty(property: Property):String?{
        remoteData.addProperty(property)?.also {
            localData.addProperty(it)
            return it.id
        }
        return null
    }
    fun watchProperties(userId:String): LiveData<List<Property>> = localData.watchProperties(userId)

}

class PropertyRemoteDataSource @Inject constructor(private val api:PropertyApi){
    suspend fun getProperties(uid:String)=api.getProperties(uid).data
    
    suspend fun addProperty(property: Property):Property? {
        Log.d("myapp","about to call api")
        val response =  api.addProperty(property)
        Log.d("myapp","Got api result")
        Log.d("myapp","${response.error}")
        return response.data
    }
}

class PropertyLocalDataSource @Inject constructor(val dao:PropertyDao){
    suspend fun addProperties(properties:List<Property>){
        dao.addProperty(properties.map{it.toEntity()})
    }
    suspend fun addProperty(property:Property) = dao.addProperty(property.toEntity())

    suspend fun getProperty(propertyId:String):Property? = dao.getProperty(propertyId)?.toModel()
    
    suspend fun getProperties(uid: String) :List<Property>{
        return dao.listProperties(uid).map{it.toModel()}
    }

    fun watchProperties(uid:String):LiveData<List<Property>>{
        return dao.watchProperties(uid).map { it.map{p->p.toModel()}}
    }
}

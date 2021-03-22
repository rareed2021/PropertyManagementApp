package com.test.propertymanagementapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.test.propertymanagementapp.data.models.enums.AccountType
import java.io.Serializable


data class Property(
    @SerializedName("address")
    var address: String? = null,
    @SerializedName("city")
    var city: String? = null,
    @SerializedName("country")
    var country: String = "",
    @SerializedName("_id")
    var id: String? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("latitude")
    var latitude: String? = null,
    @SerializedName("longitude")
    var longitude: String? = null,
    @SerializedName("mortageInfo")
    var mortageInfo: Boolean? = null,
    @SerializedName("propertyStatus")
    var propertyStatus: Boolean? = null,
    @SerializedName("purchasePrice")
    var purchasePrice: String? = null,
    @SerializedName("state")
    var state: String? = null,
    @SerializedName("userId")
    var userId: String? = null,
    @SerializedName("userType")
    var userType: AccountType? = null,
    @SerializedName("__v")
    var v: Int? = null
) : Serializable{
    fun toEntity() = PropertyEntity(
        address = address,
        city = city,
        country = country,
        id = id ?: "",
        image = image,
        latitude = latitude,
        longitude = longitude,
        mortageInfo = mortageInfo,
        propertyStatus = propertyStatus,
        purchasePrice = purchasePrice,
        state = state,
        userId = userId,
        userType = userType,
        v = v,
    )
    companion object{
        fun create() = Property()
        const val KEY = "PROPERTY"
    }
}

//api needs id nullable. database wants it not because primary key

@Entity(tableName = "Property")
data class PropertyEntity(
    var address: String? = null,
    var city: String? = null,
    var country: String = "",
    @PrimaryKey
    var id: String,
    var image: String? = null,
    var latitude: String? = null,
    var longitude: String? = null,
    var mortageInfo: Boolean? = null,
    var propertyStatus: Boolean? = null,
    var purchasePrice: String? = null,
    var state: String? = null,
    var userId: String? = null,
    var userType: AccountType? = null,
    var v: Int? = null
){
    fun toModel()=Property(
        address = address,
        city = city,
        country = country,
        id = if(id.isBlank())null else id,
        image = image,
        latitude = latitude,
        longitude = longitude,
        mortageInfo = mortageInfo,
        propertyStatus = propertyStatus,
        purchasePrice = purchasePrice,
        state = state,
        userId = userId,
        userType = userType,
        v = v,
    )
}

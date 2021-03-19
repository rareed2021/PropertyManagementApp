package com.test.propertymanagementapp.data.models
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName



@Entity
data class Property(
    @SerializedName("address")
    var address: String? = null,
    @SerializedName("city")
    var city: String? = null,
    @SerializedName("country")
    var country: String = "",
    @SerializedName("_id")
    @PrimaryKey
    var id: String,
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
    var userType: String? = null,
    @SerializedName("__v")
    var v: Int? = null
)


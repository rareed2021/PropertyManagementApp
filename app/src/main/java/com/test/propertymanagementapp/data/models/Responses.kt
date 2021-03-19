package com.test.propertymanagementapp.data.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val count: Int,
    val `data`: List<User>?,
    val error: Boolean,
    val message:String?
)

data class AuthResponse(
    val user:User?,
    val error:Boolean,
    val message:String?
)

data class PropertyListResponse(
    @SerializedName("count")
    var count: Int? = null,
    @SerializedName("data")
    var `data`: List<Property>? = null,
    @SerializedName("error")
    var error: Boolean? = null,
    @SerializedName("message")
    var message: Boolean? = null
)

data class PropertyResponse(
    @SerializedName("count")
    var count: Int? = null,
    @SerializedName("data")
    var `data`: Property? = null,
    @SerializedName("error")
    var error: Boolean? = null,
    @SerializedName("message")
    var message: Boolean? = null
)



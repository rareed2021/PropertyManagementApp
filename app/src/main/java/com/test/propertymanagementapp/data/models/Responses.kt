package com.test.propertymanagementapp.data.models

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



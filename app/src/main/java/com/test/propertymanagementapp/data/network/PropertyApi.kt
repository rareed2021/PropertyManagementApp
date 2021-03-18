package com.test.propertymanagementapp.data.network

import com.test.propertymanagementapp.data.models.AuthResponse
import com.test.propertymanagementapp.data.models.RegistrationUser
import com.test.propertymanagementapp.data.models.User
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST


interface PropertyApi {
    @POST("auth/login")
    suspend fun login(@Body user: RegistrationUser): AuthResponse

    @POST("auth/register")
    suspend fun register(@Body user:RegistrationUser): AuthResponse
}
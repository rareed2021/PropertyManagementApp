package com.test.propertymanagementapp.data.network

import com.test.propertymanagementapp.data.models.AuthResponse
import com.test.propertymanagementapp.data.models.PropertyResponse
import com.test.propertymanagementapp.data.models.RegistrationUser
import com.test.propertymanagementapp.data.models.User
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface PropertyApi {
    @POST("auth/login")
    suspend fun login(@Body user: RegistrationUser): AuthResponse

    @POST("auth/register")
    suspend fun register(@Body user:RegistrationUser): AuthResponse

    @GET("property/user/{id}")
    suspend fun getProperties(@Path("id") userId:String):PropertyResponse

}
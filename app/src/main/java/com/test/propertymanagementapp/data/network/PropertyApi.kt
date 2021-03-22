package com.test.propertymanagementapp.data.network

import com.test.propertymanagementapp.data.models.*
import retrofit2.http.*


interface PropertyApi {
    @POST("auth/login")
    suspend fun login(@Body user: RegistrationUser): AuthResponse

    @POST("auth/register")
    suspend fun register(@Body user:RegistrationUser): AuthResponse

    @GET("property/user/{id}")
    suspend fun getProperties(@Path("id") userId:String):PropertyListResponse

    @POST("property")
    suspend fun addProperty(@Body property: Property):PropertyResponse

    @PUT("property/{id}")
    suspend fun updateProperty(@Path("id") id:String, @Body property: Property):PropertyResponse
}
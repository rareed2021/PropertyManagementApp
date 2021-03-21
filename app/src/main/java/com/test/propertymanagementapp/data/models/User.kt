package com.test.propertymanagementapp.data.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.test.propertymanagementapp.data.models.enums.AccountType


@Entity
data class User(
    val __v: Int?=null,
    @PrimaryKey
    val _id: String,
    val createdAt: String?=null,
    val email: String?=null,
    val landlordEmail: String?=null,
    val name: String?=null,
    val password: String?=null,
    val type: AccountType?=null
)

data class RegistrationUser(
    @Expose
    var email: String?=null,
    @Expose
    var landlordEmail: String?=null,
    @Expose
    var name: String?=null,
    @Expose
    var password: String?=null,
    @Expose
    var type: AccountType?=null,
    var confirmPassword:String?=null
)
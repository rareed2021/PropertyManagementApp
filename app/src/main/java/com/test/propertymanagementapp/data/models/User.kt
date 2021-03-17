package com.test.propertymanagementapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
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
    var email: String?=null,
    var landlordEmail: String?=null,
    var name: String?=null,
    var password: String?=null,
    var type: AccountType?=null
)
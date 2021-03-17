package com.test.propertymanagementapp.data.models

import com.test.propertymanagementapp.data.models.enums.AccountType


data class User(
    val __v: Int?=null,
    val _id: String?=null,
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
package com.test.propertymanagementapp.data.models.enums

enum class AccountType {
    landlord,
    tenant,
    vendor,
    manager;
    companion object {
        const val KEY="AccountType"
    }
}
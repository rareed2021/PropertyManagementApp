package com.test.propertymanagementapp.ui.auth

import javax.inject.Inject

class AuthValidator @Inject constructor(){
    fun validateRegistration(name: String?, email: String?, landlordEmail: String?, password: String?, confirmPassword: String?, checkTenant: Boolean = false): AuthValidationResult {
        return when {
            email?.isNullOrBlank() == true -> AuthValidationResult.EmailBlank("Please provide email")
            password?.isNullOrBlank() == true -> AuthValidationResult.PasswordBlank("Password must not be blank")
            name?.isNullOrBlank() == true -> AuthValidationResult.NameBlank("Please provide name")
            checkTenant && landlordEmail?.isNullOrBlank() == true -> AuthValidationResult.NoLandlordEmail("Please provide landlord email")
            confirmPassword != password -> AuthValidationResult.PasswordsDontMatch("Passwords do not match")
            else -> AuthValidationResult.Success
        }
    }
}

sealed class AuthValidationResult {
    open class ValidationError(open val message:String) : AuthValidationResult()
    data class EmailBlank( override val message: String) : ValidationError(message)
    data class PasswordBlank( override val message: String) : ValidationError(message)
    data class NameBlank( override val message: String) : ValidationError(message)
    data class NoLandlordEmail( override val message: String) : ValidationError(message)
    data class PasswordsDontMatch( override val message: String) : ValidationError(message)
    object Success : AuthValidationResult()
}
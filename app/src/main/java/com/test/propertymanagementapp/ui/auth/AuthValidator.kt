package com.test.propertymanagementapp.ui.auth

import com.test.propertymanagementapp.data.models.RegistrationUser
import javax.inject.Inject

class AuthValidator @Inject constructor(){
    fun validateRegistration(name: String?, email: String?, landlordEmail: String?, password: String?, confirmPassword: String?, checkTenant: Boolean = false): AuthValidationResult {
        return when {
            email.isNullOrBlank() -> AuthValidationResult.EmailBlank
            password.isNullOrBlank() -> AuthValidationResult.PasswordBlank
            name.isNullOrBlank() -> AuthValidationResult.NameBlank
            checkTenant && landlordEmail.isNullOrBlank() -> AuthValidationResult.NoLandlordEmail
            confirmPassword != password -> AuthValidationResult.PasswordsDontMatch
            else -> AuthValidationResult.Success
        }
    }
    fun validateLogin(user:RegistrationUser):AuthValidationResult{
        return when{
            user.email.isNullOrBlank()-> AuthValidationResult.EmailBlank
            user.password.isNullOrBlank()->AuthValidationResult.PasswordBlank
            else -> AuthValidationResult.Success
        }
    }
}



enum class AuthValidationResult(val message:String){
    EmailBlank("Please provide email"),
    PasswordBlank("Password must not be blank"),
    NameBlank("Please provide name"),
    NoLandlordEmail("Please provide landlord email"),
    PasswordsDontMatch("Passwords do not match"),
    Success("")
}

//sealed class AuthValidationResult {
//    open class ValidationError(open val message:String) : AuthValidationResult()
//    data class EmailBlank( override val message: String) : ValidationError(message)
//    data class PasswordBlank( override val message: String) : ValidationError(message)
//    data class NameBlank( override val message: String) : ValidationError(message)
//    data class NoLandlordEmail( override val message: String) : ValidationError(message)
//    data class PasswordsDontMatch( override val message: String) : ValidationError(message)
//    object Success : AuthValidationResult()
//}
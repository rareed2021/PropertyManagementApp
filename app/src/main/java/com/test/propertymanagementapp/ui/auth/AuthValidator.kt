package com.test.propertymanagementapp.ui.auth

import android.util.Log
import com.test.propertymanagementapp.data.models.RegistrationUser
import java.util.*
import javax.inject.Inject

class AuthValidator @Inject constructor() {
    fun validateRegistration(
        name: String?,
        email: String?,
        landlordEmail: String?,
        password: String?,
        confirmPassword: String?,
        checkTenant: Boolean = false
    )
            : EnumSet<AuthValidationResult> {
        val ret = EnumSet.noneOf(AuthValidationResult::class.java)
        if (email.isNullOrBlank())
            ret.add(AuthValidationResult.EmailBlank)
        if (password.isNullOrBlank())
            ret.add(AuthValidationResult.PasswordBlank)
        if (name.isNullOrBlank())
            ret.add(AuthValidationResult.NameBlank)
        if (checkTenant && landlordEmail.isNullOrBlank())
            ret.add(AuthValidationResult.NoLandlordEmail)
        if (confirmPassword != password)
            ret.add(AuthValidationResult.PasswordsDontMatch)
        return ret;
    }

    fun validateLogin(user: RegistrationUser): EnumSet<AuthValidationResult> {
        val ret = EnumSet.noneOf(AuthValidationResult::class.java)
        if (user.email.isNullOrBlank())
            ret.add(AuthValidationResult.EmailBlank)
        if (user.password.isNullOrBlank())
            ret.add(AuthValidationResult.PasswordBlank)
        return ret
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
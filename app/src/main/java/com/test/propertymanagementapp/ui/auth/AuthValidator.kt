package com.test.propertymanagementapp.ui.auth

import android.util.Log
import com.test.propertymanagementapp.data.models.RegistrationUser
import com.test.propertymanagementapp.data.models.enums.AccountType
import java.util.*
import javax.inject.Inject

class AuthValidator @Inject constructor() {
    fun validateRegistration(
        user:RegistrationUser,
    ): EnumSet<AuthValidationResult> {
        val ret = EnumSet.noneOf(AuthValidationResult::class.java)
        user?.apply {
        if (email.isNullOrBlank())
            ret.add(AuthValidationResult.EmailBlank)
        if (password.isNullOrBlank())
            ret.add(AuthValidationResult.PasswordBlank)
        if (name.isNullOrBlank())
            ret.add(AuthValidationResult.NameBlank)
        if (type==AccountType.tenant && landlordEmail.isNullOrBlank())
            ret.add(AuthValidationResult.NoLandlordEmail)
        if (confirmPassword != password)
            ret.add(AuthValidationResult.PasswordsDontMatch)
        }
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
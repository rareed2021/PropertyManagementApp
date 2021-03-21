package com.test.propertymanagementapp.ui.auth

import com.google.common.truth.Truth
import com.test.propertymanagementapp.data.models.RegistrationUser
import com.test.propertymanagementapp.data.models.enums.AccountType
import com.test.propertymanagementapp.ui.auth.AuthValidationResult.*
import org.junit.Test

class AuthValidatorRegistrationTest {
    val validLandlord = RegistrationUser(
        "testperson@example.com",
        null,
        "Test Person",
        "abcdef",
        AccountType.landlord,
        "abcdef"
    )
    val emptyUser = RegistrationUser(type= AccountType.tenant)
    val validTenant = RegistrationUser(
        "persontest@example.com",
        "landlordEmail@example.com",
        "Test Person",
        "abcdef",
        AccountType.landlord,
        "abcdef"
    )

    @Test
    fun `Should be valid landlord`(){
        Truth.assertThat(AuthValidator().validateRegistration(validLandlord)).isEmpty()
    }
    @Test
    fun `Should be valid tenant`(){
        Truth.assertThat(AuthValidator().validateRegistration(validTenant)).isEmpty()
    }

    @Test
    fun `Tenants must have landlord email`(){
        Truth.assertThat(AuthValidator().validateRegistration(validLandlord.copy(type=AccountType.tenant))).contains(NoLandlordEmail)
    }

    @Test
    fun `Passwords must match`(){
        Truth.assertThat(AuthValidator().validateRegistration(validLandlord.copy(confirmPassword = "beft"))).contains(PasswordsDontMatch)
    }

    @Test
    fun `Passwords don't match if one is null`(){
        Truth.assertThat(AuthValidator().validateRegistration(validLandlord.copy(confirmPassword = null))).contains(PasswordsDontMatch)
    }


    @Test
    fun `Blank user should have all registration errors except passwords don't match`(){
        Truth.assertThat(AuthValidator().validateRegistration(emptyUser)).containsExactly(
            PasswordBlank,
            EmailBlank,
            NameBlank,
            NoLandlordEmail
        )
    }
}
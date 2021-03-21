package com.test.propertymanagementapp.ui.auth

import com.google.common.truth.Truth.assertThat
import com.test.propertymanagementapp.data.models.RegistrationUser
import com.test.propertymanagementapp.data.models.enums.AccountType
import org.junit.Test

class AuthValidatorLoginTest {

    val validLandlord = RegistrationUser(
        "testperson@example.com",
        null,
        "Test Person",
        "abcdef",
        AccountType.landlord
    )
    val emptyUser = RegistrationUser(type=AccountType.tenant)
    val validTenant = RegistrationUser(
        "persontest@example.com",
        "landlordEmail@example.com",
        "Test Person",
        "abcdef",
        AccountType.landlord
    )

    @Test
    fun `Should be valid landlord`(){
        assertThat(AuthValidator().validateLogin(validLandlord)).isEmpty()
    }
    @Test
    fun `Should be valid tenant`(){
        assertThat(AuthValidator().validateLogin(validTenant)).isEmpty()
    }

    @Test
    fun `No email should return EmailBlank`(){
        assertThat(AuthValidator().validateLogin(validLandlord.copy(email=null)))
    }

    @Test
    fun `User must supply password`(){
        assertThat(AuthValidator().validateLogin(validLandlord.copy(password= null)))
    }
    @Test
    fun `Blank user should have all login errors`(){
        assertThat(AuthValidator().validateLogin(emptyUser)).containsExactly(
            AuthValidationResult.PasswordBlank,
            AuthValidationResult.EmailBlank,
        )
    }

}
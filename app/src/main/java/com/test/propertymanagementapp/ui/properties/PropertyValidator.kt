package com.test.propertymanagementapp.ui.properties

import com.test.propertymanagementapp.data.models.Property
import java.util.*
import javax.inject.Inject

class PropertyValidator @Inject constructor() {
    fun validateNewProperty(property:Property?):EnumSet<PropertyValidatorError>{
        val ret = EnumSet.noneOf(PropertyValidatorError::class.java)
        if(property==null){
            ret.add(PropertyValidatorError.PropertyMissing)
        }
        property?.apply {
            if(address.isNullOrBlank())
                ret.add(PropertyValidatorError.AddressBlank)
            if(city.isNullOrBlank())
                ret.add(PropertyValidatorError.CityBlank)
            if(state.isNullOrBlank())
                ret.add(PropertyValidatorError.ProvinceBlank)
            if(userId.isNullOrBlank())
                ret.add(PropertyValidatorError.UserNotSet)
            if(userType==null)
                ret.add(PropertyValidatorError.UserTypeNotSet)

        }
        return ret
    }
}


enum class PropertyValidatorError(val message:String){
    PropertyMissing("Property is missing"),
    AddressBlank("Address must not be blank"),
    CityBlank("City must not be blank"),
    ProvinceBlank("Province/State must not be blank"),
    UserNotSet("User Id is not set"),
    UserTypeNotSet("User type is absent")
}

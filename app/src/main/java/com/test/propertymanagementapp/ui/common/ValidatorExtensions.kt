package com.test.propertymanagementapp.ui.common

import android.util.Log
import com.test.propertymanagementapp.ui.auth.AuthValidationResult
import com.test.propertymanagementapp.ui.properties.PropertyValidatorError
import java.util.*
import kotlin.reflect.KClass


fun EnumSet<*>.logAll(prefix:String=""){
    for(i in this){
        val str = if (prefix.isBlank()) "" else " - $prefix"
        Log.d("myapp","validation error$str: $i")
    }
}
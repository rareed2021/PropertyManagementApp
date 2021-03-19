package com.test.propertymanagementapp.data.database

import androidx.room.TypeConverter
import com.test.propertymanagementapp.data.models.enums.AccountType

class Converters {
    @TypeConverter
    fun toAccountType(value:String?) : AccountType? = value?.let {  enumValueOf<AccountType>(value)}

    @TypeConverter
    fun fromAccountType(value:AccountType?) = value?.name
}
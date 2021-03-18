package com.test.propertymanagementapp.ui.properties

import com.test.propertymanagementapp.data.repositories.AuthRepository
import com.test.propertymanagementapp.data.repositories.PropertyRepository
import com.test.propertymanagementapp.ui.common.BaseViewModel

class PropertyViewModel(val auth:AuthRepository, val repository:PropertyRepository)  : BaseViewModel(){

}
package com.test.propertymanagementapp.ui.properties

import android.util.Log
import android.view.View
import androidx.databinding.adapters.SpinnerBindingAdapter
import androidx.lifecycle.MutableLiveData
import com.test.propertymanagementapp.data.models.Property
import com.test.propertymanagementapp.data.repositories.AuthRepository
import com.test.propertymanagementapp.data.repositories.PropertyRepository
import com.test.propertymanagementapp.ui.common.BaseViewModel

class PropertyViewModel(val auth:AuthRepository, val repository:PropertyRepository)  : BaseViewModel(){
    val properties = MutableLiveData<List<Property>>()
    val currentProperty = MutableLiveData<Property>()



    fun submitProperty(view: View){
        Log.d("myapp","Submitting property: ${currentProperty.value}")
    }
}
package com.test.propertymanagementapp.ui.properties

import android.util.Log
import android.view.View
import androidx.databinding.adapters.SpinnerBindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.propertymanagementapp.data.models.Property
import com.test.propertymanagementapp.data.repositories.AuthRepository
import com.test.propertymanagementapp.data.repositories.PropertyRepository
import com.test.propertymanagementapp.ui.common.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PropertyViewModel(val auth:AuthRepository, val repository:PropertyRepository, val validator:PropertyValidator)  : BaseViewModel(){
    val properties = MutableLiveData<List<Property>>()
    val currentProperty = MutableLiveData<Property>()


    fun newProperty(){
        viewModelScope.launch {
            val user = auth.getUser()
            Log.d("myapp","$user")
            user?.also {
                val newProp = Property(id="",
                    mortageInfo = false,
                    userId = it._id,
                    userType = it.type)
                currentProperty.value = newProp
            }
        }
    }

    fun submitProperty(view: View){
        val issues = validator.validateNewProperty(currentProperty.value)
        if(issues.isEmpty()) {
            Log.d("myapp", "Submitting property: ${currentProperty.value}")
        }else{
            _error.value = issues.first().message
            for(i in issues){
                Log.d("myapp", "Error validating property $i")
            }
        }
    }
}
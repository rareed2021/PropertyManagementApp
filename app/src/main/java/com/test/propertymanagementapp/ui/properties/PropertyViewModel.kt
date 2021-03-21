package com.test.propertymanagementapp.ui.properties

import android.util.Log
import android.view.View
import androidx.databinding.adapters.SpinnerBindingAdapter
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.test.propertymanagementapp.data.models.Property
import com.test.propertymanagementapp.data.models.PropertyResponse
import com.test.propertymanagementapp.data.repositories.AuthRepository
import com.test.propertymanagementapp.data.repositories.PropertyRepository
import com.test.propertymanagementapp.ui.common.BaseViewModel
import com.test.propertymanagementapp.ui.common.ListType
import com.test.propertymanagementapp.ui.common.ListViewModel
import com.test.propertymanagementapp.ui.common.logAll
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PropertyViewModel(
    private val auth: AuthRepository,
    private val repository: PropertyRepository,
    private val validator: PropertyValidator
) : ListViewModel<Property>(Property::create) {
    val properties = MediatorLiveData<List<Property>>()
    //val currentProperty = MutableLiveData<Property>()
    val activityType = MutableLiveData<ListType>().apply { value = ListType.EDIT }

    init {
        viewModelScope.launch {
            val user = auth.getUser()
            if (user != null) {
                repository.getProperties(user._id)
                properties.addSource(repository.watchProperties(user._id)) {
                    Log.d("myapp", "Getting properties: $it")
                    properties.value = it
                }
            }
        }
    }

    override fun beginAdd() {
        super.beginAdd()
        viewModelScope.launch {
            val user = auth.getUser()
            user?.also {
                current.value?.apply {
                    mortageInfo = false
                    userId = it._id
                    userType = it.type
                }
            }
        }
    }

//    fun newProperty() {
//        viewModelScope.launch {
//            _state.value = State.INITIALIZED
//            val user = auth.getUser()
//            Log.d("myapp", "$user")
//            user?.also {
//                val newProp = Property(
//                    mortageInfo = false,
//                    userId = it._id,
//                    userType = it.type
//                )
//                currentProperty.value = newProp
//            }
//        }
//    }

    fun submitProperty(view: View) {
        val property = current.value
        val issues = validator.validateNewProperty(property)
        _state.value = State.PENDING
        if (issues.isEmpty()) {
            viewModelScope.launch {
                try {
                    Log.d("myapp", "Submitting property: $property")
                    if (property != null) repository.addProperty(property)
                    _state.value = State.FINISHED
                } catch (e: HttpException) {
                    val response = Gson().fromJson(
                        e.response()?.errorBody()?.string() ?: "{}",
                        PropertyResponse::class.java
                    )
                    val msg = response?.message ?: "Error $e"
                    _error.value = msg
                    Log.d("myapp", msg)
                } catch (e: Throwable) {
                    _error.value = e.localizedMessage
                    Log.d("myapp", e.localizedMessage)
                }
            }
        } else {
            _error.value = issues.first().message
            issues.logAll("add property")
            _state.value = State.ERROR
        }
    }


}
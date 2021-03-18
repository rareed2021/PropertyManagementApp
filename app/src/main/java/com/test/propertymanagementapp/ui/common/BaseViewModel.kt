package com.test.propertymanagementapp.ui.common

import android.provider.Settings.Global.getString
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.propertymanagementapp.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

abstract class BaseViewModel :ViewModel(){
    val isNetworkAvailable = MutableLiveData<Boolean>().apply{ value=true }
    val _error = MutableLiveData<String>().apply { value="" }
    lateinit var job : Job

    private val networkObserver = Observer<Boolean>{
        if(!it && ::job.isInitialized && job.isActive){
            job.cancelChildren(CancellationException("No Network"))
            _error.value = "Network unavailable"
        }
    }

    init {
        isNetworkAvailable.observeForever(networkObserver)
    }

    override fun onCleared() {
        super.onCleared()
        isNetworkAvailable.removeObserver(networkObserver)
    }

    fun runAsync(callback:suspend ()->Unit){
        if(isNetworkAvailable.value==true){
            job = viewModelScope.launch {
                callback.invoke()
            }
        }else{
            _error.value="Network unavailable"
        }
    }
}
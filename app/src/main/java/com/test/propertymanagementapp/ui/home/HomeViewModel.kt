package com.test.propertymanagementapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.propertymanagementapp.data.repositories.HomeRepository

class HomeViewModel(repository: HomeRepository) : ViewModel() {

    val activities = MutableLiveData<List<ActionIcon>>()


    companion object{

    }
}
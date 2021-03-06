package com.test.propertymanagementapp.ui.home

import androidx.lifecycle.MutableLiveData
import com.test.propertymanagementapp.R
import com.test.propertymanagementapp.data.models.User
import com.test.propertymanagementapp.data.models.enums.AccountType
import com.test.propertymanagementapp.data.repositories.AuthRepository
import com.test.propertymanagementapp.data.repositories.HomeRepository
import com.test.propertymanagementapp.ui.common.BaseViewModel
import com.test.propertymanagementapp.ui.properties.PropertyHomeActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class HomeViewModel(repository: AuthRepository) : BaseViewModel() {

    val actions = MutableLiveData<List<ActionIcon>>()
    val user = MutableLiveData<User>()
    init{
        runAsync {
            val currentUser =repository.getUser()
            currentUser?.also { u->
                user.value = u
                if(u.type== AccountType.landlord){
                    actions.value = landlordActions
                }
            }
        }
    }



    companion object{
        val landlordActions = listOf(
            ActionIcon(R.drawable.ic_baseline_house_24, "Properties", nextActivity = PropertyHomeActivity::class.java),
            ActionIcon(R.drawable.ic_baseline_person_24, "Tenants"),
            ActionIcon(R.drawable.ic_baseline_check_24, "Todo")
        )
    }
}
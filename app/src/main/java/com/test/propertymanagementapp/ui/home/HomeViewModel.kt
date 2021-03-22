package com.test.propertymanagementapp.ui.home

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.propertymanagementapp.R
import com.test.propertymanagementapp.data.models.User
import com.test.propertymanagementapp.data.models.enums.AccountType
import com.test.propertymanagementapp.data.repositories.AuthRepository
import com.test.propertymanagementapp.data.repositories.HomeRepository
import com.test.propertymanagementapp.ui.common.BaseViewModel
import com.test.propertymanagementapp.ui.properties.PropertyHomeActivity
import com.test.propertymanagementapp.ui.todo.TodoListActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class HomeViewModel(val repository: AuthRepository) : BaseViewModel() {

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

    fun logout(view:View){
        viewModelScope.launch {
            repository.logout()
            _state.value = State.FINISHED
        }
    }



    companion object{
        val landlordActions = listOf(
            ActionIcon(R.drawable.ic_baseline_house_24, "Properties", nextActivity = PropertyHomeActivity::class.java),
            ActionIcon(R.drawable.ic_baseline_person_24, "Tenants"),
            ActionIcon(R.drawable.ic_baseline_check_24, "Todo", nextActivity = TodoListActivity::class.java)
        )
    }
}
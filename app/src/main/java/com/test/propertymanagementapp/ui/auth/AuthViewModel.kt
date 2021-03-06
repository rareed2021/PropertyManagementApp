package com.test.propertymanagementapp.ui.auth

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.test.propertymanagementapp.data.models.AuthResponse
import com.test.propertymanagementapp.data.models.RegistrationUser
import com.test.propertymanagementapp.data.models.enums.AccountType
import com.test.propertymanagementapp.data.repositories.AuthRepository
import com.test.propertymanagementapp.ui.common.BaseViewModel
import com.test.propertymanagementapp.ui.common.logAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AuthViewModel(private val repository: AuthRepository, private val validator: AuthValidator) :
    BaseViewModel() {
    val user = RegistrationUser()
    //val confirmPassword = MutableLiveData<String>().apply { value = "" }
    val accountType = MutableLiveData<AccountType>().apply { value = AccountType.tenant }
    val currentAction = MutableLiveData<AuthAction>().apply { value = AuthAction.PENDING }


    fun loginButtonClick(view: View) {
//        val password = password.value
//        val email = email.value
        _error.value=""
        val email = user.email
        val password = user.password
        val user = user
        Log.d("myapp", "Checking Login")
        val result = validator.validateLogin(user)
        if (result.isEmpty()) {
            viewModelScope.launch {
                try {
                    //should be safe to cast because validator checks
                    handleSuccess(repository.login(email!!, password!!), AuthAction.LOGIN)
                } catch (e: Throwable) {
                    handleError(e)
                }
            }
        } else {
            _error.value = result.first().message
            result.logAll("Login")
        }
    }


    fun registerUser(view: View) {
        Log.d("myapp", "Begining registration")
        _error.value=""
//        val password = user.password
//        val email = user.email
//        val landlordEmail = user.landlordEmail
//        val type = accountType.value
//        val confirmPassword = confirmPassword.value
//        val name = user.name
        val user = user.copy()//take immutable snapshot for validation
        val result = validator.validateRegistration(user)
        if (result.isEmpty()) {
            viewModelScope.launch(Dispatchers.Default) {
                try {
                    handleSuccess(repository.register(user), AuthAction.REGISTER)
                } catch (e: Throwable) {
                    handleError(e)
                }
            }
        } else {
            _error.value = result.first().message
            result.logAll("Registration")
        }
    }

    private suspend fun handleSuccess(response: AuthResponse, successAction: AuthAction) {
        Log.d("myapp", "success: $response")
        withContext(Dispatchers.Main) {
            if (response.error) {
                Log.d("myapp", "Error: ${response.message}")
                _error.value = response.message ?: "Error encountered"
            } else {
                currentAction.value = successAction
            }
        }
    }

    private suspend fun handleError(e: Throwable) {
        withContext(Dispatchers.Main) {
            if (e is HttpException) {
                val msg = e.response()?.errorBody()?.string()
                Log.d("myapp", msg ?: "Error")
                val response = Gson().fromJson(msg, AuthResponse::class.java)
                val err = response?.message ?: e.localizedMessage ?: "Error"
                Log.e("myapp", err)
                _error.value = err
            } else {
                Log.e("myapp", "Generic error")
                Log.e("myapp", "$e    ${e.message}    ${e.localizedMessage}")
                _error.value = e.localizedMessage
            }

        }
    }


//    inner class AuthObserver(private val successAction:AuthAction) : SingleObserver<AuthResponse>{
//        override fun onSubscribe(d: Disposable) {
//        }
//
//        override fun onSuccess(t: AuthResponse) {
//            Log.d("myapp","success: $t")
//            if(t.error) {
//                Log.d("myapp","Error: ${t.message}")
//
////                val response = Gson().fromJson()
//                errorMessage.value = t.message ?: "Error encountered"
//            }else{
//                currentAction.value = successAction
//            }
//        }
//
//        override fun onError(e: Throwable) {
//            Log.d("myapp","error: $e")
//            if(e is HttpException){
//                val msg = e.response()?.errorBody()?.string()
//                Log.d("myapp",msg?:"Error")
//                val response = Gson().fromJson(msg, AuthResponse::class.java)
//                val err = response?.message ?: e.localizedMessage?:"Error"
//                Log.d("myapp",err)
//                errorMessage.value =err
//            }else{
//                errorMessage.value = e.localizedMessage
//            }
//        }
//
////        override fun onNext(t: AuthResponse) {
////            Log.d("myapp","success: $t")
////            if(t.error) {
////                Log.d("myapp","Error: ${t.message}")
////
//////                val response = Gson().fromJson()
////                errorMessage.value = t.message ?: "Error encountered"
////            }else{
////                currentAction.value = successAction
////            }
////        }
////
////        override fun onComplete() {
////        }
//
//    }
}
package com.test.propertymanagementapp.ui.auth

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.test.propertymanagementapp.data.models.AuthResponse
import com.test.propertymanagementapp.data.models.RegistrationUser
import com.test.propertymanagementapp.data.models.enums.AccountType
import com.test.propertymanagementapp.data.repositories.AuthRepository
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class AuthViewModel(private val repository: AuthRepository, private val validator: AuthValidator)  : ViewModel(){
    val user = RegistrationUser()
    val confirmPassword = MutableLiveData<String>().apply { value="" }
    val accountType = MutableLiveData<AccountType>().apply { value=AccountType.tenant }
    val errorMessage =  MutableLiveData<String>().apply { value="" }
    val currentAction = MutableLiveData<AuthAction>().apply{value=AuthAction.PENDING}



    fun loginButtonClick(view: View){
//        val password = password.value
//        val email = email.value
        val email = user.email
        val password = user.password
        val user = user
        Log.d("myapp","Checking Login")
        val result = validator.validateLogin(user)
        if(result==AuthValidationResult.Success) {
            //should be safe to cast because validator checks
            repository.login(email!!, password!!)
                .subscribe(AuthObserver(AuthAction.LOGIN))
        }else{
            errorMessage.value = result.message
        }
    }



    fun registerUser(view:View){
        Log.d("myapp","Begining registration")
        val password = user.password
        val email = user.email
        val landlordEmail = user.landlordEmail
        val type = accountType.value
        val confirmPassword = confirmPassword.value
        val name = user.name
        val result = validator.validateRegistration(
                name = name,
                password = password,
                confirmPassword = confirmPassword,
                email = email,
                landlordEmail = landlordEmail,
                checkTenant = type==AccountType.tenant
        )
        if(result==AuthValidationResult.Success){
            val user = RegistrationUser(name=name,
                password = password,
                email = email,
                landlordEmail = landlordEmail,
                type = type
            )
            repository.register(user)
                .subscribe(AuthObserver(AuthAction.REGISTER))
        }else{
            errorMessage.value = result.message
        }
    }


    inner class AuthObserver(private val successAction:AuthAction) : SingleObserver<AuthResponse>{
        override fun onSubscribe(d: Disposable) {
        }

        override fun onSuccess(t: AuthResponse) {
            Log.d("myapp","success: $t")
            if(t.error) {
                Log.d("myapp","Error: ${t.message}")

//                val response = Gson().fromJson()
                errorMessage.value = t.message ?: "Error encountered"
            }else{
                currentAction.value = successAction
            }
        }

        override fun onError(e: Throwable) {
            Log.d("myapp","error: $e")
            if(e is HttpException){
                val msg = e.response().errorBody()?.string()
                Log.d("myapp",msg?:"Error")
                val response = Gson().fromJson(msg, AuthResponse::class.java)
                val err = response?.message ?: e.localizedMessage?:"Error"
                Log.d("myapp",err)
                errorMessage.value =err
            }else{
                errorMessage.value = e.localizedMessage
            }
        }

//        override fun onNext(t: AuthResponse) {
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
//        override fun onComplete() {
//        }

    }
}
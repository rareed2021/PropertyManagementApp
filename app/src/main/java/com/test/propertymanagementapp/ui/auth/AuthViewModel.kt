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
//    val email = MutableLiveData<String>().apply { value="" }
//    val password = MutableLiveData<String>().apply { value="" }
    val confirmPassword = MutableLiveData<String>().apply { value="" }
//    val name = MutableLiveData<String>().apply { value="" }
//    val landlordEmail = MutableLiveData<String>().apply { value="" }
    val accountType = MutableLiveData<AccountType>().apply { value=AccountType.tenant }
//    val showLandLordEmail = accountType.map { it==AccountType.tenant}
    val errorMessage =  MutableLiveData<String>().apply { value="" }
    val currentAction = MutableLiveData<AuthAction>().apply{value=AuthAction.PENDING}



    fun loginButtonClick(view: View){
//        val password = password.value
//        val email = email.value
        val email = user.email
        val password = user.password
        Log.d("myapp","Checking Login")
        if(!email.isNullOrBlank() && !password.isNullOrBlank()) {
            Log.d("myapp", "Trying Login")
            val foo = repository.login(email, password)
                .subscribe(AuthObserver(AuthAction.LOGIN))
        }
    }



//
//    private fun validateRegister(checkTenant:Boolean=false):Boolean{
//        errorMessage.value = when{
//            email.value.isNullOrBlank()->"Please provide email"
//            password.value.isNullOrBlank() ->"Password must not be blank"
//            name.value.isNullOrBlank() -> "Please provide name"
//            checkTenant && landlordEmail.value.isNullOrBlank() -> "Please provide landlord email"
//            confirmPassword.value != password.value -> "Passwords do not match"
//            else->{
//                errorMessage.value=""
//                return true
//            }
//        }
//        return false
//    }

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
                type = accountType.value
            )
            Log.d("myapp","About to register")
            Log.d("myapp","User is $user")
            repository.register(user)
                .subscribe(AuthObserver(AuthAction.REGISTER))
        }else if (result is AuthValidationResult.ValidationError){
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
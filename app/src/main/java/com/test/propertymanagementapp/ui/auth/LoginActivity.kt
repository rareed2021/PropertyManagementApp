package com.test.propertymanagementapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.test.propertymanagementapp.app.CustomApplication
import com.test.propertymanagementapp.app.ViewModelFactory
import com.test.propertymanagementapp.databinding.ActivityLoginBinding
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    lateinit var viewmodel:AuthViewModel
    @Inject
    lateinit var factory:ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        (application as CustomApplication).component.inject(this)
        viewmodel = ViewModelProvider(this,factory).get(AuthViewModel::class.java)
        setContentView(binding.root)
        init()
        //setContentView(R.layout.activity_login)
    }

    private fun init() {
        binding.viewmodel=viewmodel
        binding.lifecycleOwner=this
        viewmodel.currentAction.observe(this){
            when(it){
                AuthAction.LOGIN->Toast.makeText(this,"Logged in successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
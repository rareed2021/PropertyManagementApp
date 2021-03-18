package com.test.propertymanagementapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.test.propertymanagementapp.app.CustomApplication
import com.test.propertymanagementapp.databinding.ActivityLoginBinding
import com.test.propertymanagementapp.ui.home.HomeActivity
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    @Inject
    lateinit var viewmodel:AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        (application as CustomApplication).component
            .activityComponent.create(this)
            .inject(this)
        setContentView(binding.root)
        init()
        //setContentView(R.layout.activity_login)
    }

    private fun init() {
        binding.viewmodel=viewmodel
        binding.lifecycleOwner=this
        viewmodel.currentAction.observe(this){
            when(it){
//                AuthAction.LOGIN->Toast.makeText(this,"Logged in successfully", Toast.LENGTH_SHORT).show()
                AuthAction.LOGIN->{
                    startActivity(Intent(this, HomeActivity::class.java))
                    finishAffinity()
                }
            }
        }
    }
}
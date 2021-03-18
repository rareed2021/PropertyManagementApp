package com.test.propertymanagementapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.test.propertymanagementapp.R
import com.test.propertymanagementapp.app.CustomApplication
import com.test.propertymanagementapp.databinding.ActivityMainBinding
import com.test.propertymanagementapp.ui.home.HomeActivity
import com.test.propertymanagementapp.ui.home.HomeViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    @Inject
    lateinit var viewmodel:HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as CustomApplication).component.activityComponent.create(this).inject(this)
        init()
    }

    private fun init() {
        Log.d("myapp","TEST TEST")
        val u = viewmodel.user.value
        Log.d("myapp","User Val is $u")
        viewmodel.user.observe(this){
            startActivity(Intent(this,HomeActivity::class.java))
        }
        binding.buttonLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
        binding.buttonRegister.setOnClickListener {
            startActivity(Intent(this,RegistrationActivity::class.java))
        }
    }
}
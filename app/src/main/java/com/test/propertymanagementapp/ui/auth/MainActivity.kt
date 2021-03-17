package com.test.propertymanagementapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.test.propertymanagementapp.R
import com.test.propertymanagementapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        Log.d("myapp","TEST TEST")
        binding.buttonLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
        binding.buttonRegister.setOnClickListener {
            startActivity(Intent(this,RegistrationActivity::class.java))
        }
    }
}
package com.test.propertymanagementapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.propertymanagementapp.R
import com.test.propertymanagementapp.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.pagerRegister.adapter = AuthPagerAdapter(supportFragmentManager)
        binding.tabsRegister.setupWithViewPager(binding.pagerRegister)
    }
}
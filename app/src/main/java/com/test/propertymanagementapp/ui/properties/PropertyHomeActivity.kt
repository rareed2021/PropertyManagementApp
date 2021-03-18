package com.test.propertymanagementapp.ui.properties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.propertymanagementapp.R
import com.test.propertymanagementapp.databinding.ActivityHomeBinding
import com.test.propertymanagementapp.databinding.ActivityPropertyHomeBinding


class PropertyHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityPropertyHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPropertyHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
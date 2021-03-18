package com.test.propertymanagementapp.ui.properties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.propertymanagementapp.databinding.ActivityAddPropertyBinding
import javax.inject.Inject

class AddPropertyActivity : AppCompatActivity() {
    @Inject
    lateinit var viewmodel : PropertyViewModel
    lateinit var binding:ActivityAddPropertyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {

    }
}
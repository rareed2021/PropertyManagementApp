package com.test.propertymanagementapp.ui.properties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.propertymanagementapp.R
import com.test.propertymanagementapp.app.CustomApplication
import com.test.propertymanagementapp.databinding.ActivityHomeBinding
import com.test.propertymanagementapp.databinding.ActivityPropertyHomeBinding
import com.test.propertymanagementapp.ui.home.HomeViewModel
import javax.inject.Inject


class PropertyHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityPropertyHomeBinding
    @Inject
    lateinit var viewmodel:HomeViewModel
    @Inject lateinit var adapter: PropertyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPropertyHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as CustomApplication).component.activityComponent.create(this).inject(this)
        init()
    }

    private fun init() {
        binding.recyclerProperties.adapter = adapter
        binding.recyclerProperties.layoutManager = LinearLayoutManager(this)
    }
}
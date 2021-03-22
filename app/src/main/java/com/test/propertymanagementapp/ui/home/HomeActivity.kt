package com.test.propertymanagementapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.propertymanagementapp.R
import com.test.propertymanagementapp.app.CustomApplication
import com.test.propertymanagementapp.databinding.ActivityHomeBinding
import com.test.propertymanagementapp.ui.common.BaseViewModel
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {
    @Inject
    lateinit var viewmodel:HomeViewModel

    @Inject
    lateinit var adapter:ActionButtonsAdapter
    lateinit var binding:ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as CustomApplication).component.activityComponent.create(this).inject(this)
        init()
    }

    private fun init() {
        binding.recyclerActionItems.adapter = adapter
        binding.recyclerActionItems.layoutManager = GridLayoutManager(this,3)
        viewmodel._state.observe(this){
            if(it==BaseViewModel.State.FINISHED)
                finish()
        }
    }
}
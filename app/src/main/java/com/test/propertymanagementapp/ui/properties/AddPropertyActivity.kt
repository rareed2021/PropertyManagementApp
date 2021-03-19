package com.test.propertymanagementapp.ui.properties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.autofill.HintConstants
import com.test.propertymanagementapp.app.CustomApplication
import com.test.propertymanagementapp.data.models.Property
import com.test.propertymanagementapp.databinding.ActivityAddPropertyBinding
import javax.inject.Inject

class AddPropertyActivity : AppCompatActivity() {
    @Inject
    lateinit var viewmodel : PropertyViewModel
    lateinit var binding:ActivityAddPropertyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        (application as CustomApplication).component.activityComponent.create(this).inject(this)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        viewmodel.newProperty()
        binding.viewmodel = viewmodel
        binding.lifecycleOwner=this
    }
}
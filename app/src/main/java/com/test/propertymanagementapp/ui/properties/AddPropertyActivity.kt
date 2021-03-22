package com.test.propertymanagementapp.ui.properties

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.autofill.HintConstants
import com.test.propertymanagementapp.app.CustomApplication
import com.test.propertymanagementapp.data.models.Property
import com.test.propertymanagementapp.databinding.ActivityAddPropertyBinding
import com.test.propertymanagementapp.ui.common.BaseViewModel
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
        val property = intent.getSerializableExtra(Property.KEY) as? Property
        if(property==null){
            viewmodel.beginAdd()
        }else{
            viewmodel.beginEdit(property)
        }
        binding.viewmodel = viewmodel
        binding.lifecycleOwner=this
        viewmodel._state.observe(this){
            when(it){
                BaseViewModel.State.FINISHED ->{
                    finish()
                }
            }
        }
    }
}
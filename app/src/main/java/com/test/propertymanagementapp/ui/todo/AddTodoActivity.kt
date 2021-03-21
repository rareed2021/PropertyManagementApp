package com.test.propertymanagementapp.ui.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.test.propertymanagementapp.R
import com.test.propertymanagementapp.app.CustomApplication
import com.test.propertymanagementapp.data.models.enums.TodoPriority
import com.test.propertymanagementapp.databinding.ActivityAddTodoBinding
import com.test.propertymanagementapp.databinding.ActivityTodoListBinding
import javax.inject.Inject

class AddTodoActivity : AppCompatActivity() {
    @Inject
    lateinit var viewmodel : TodoViewModel
    @Inject
    lateinit var adapter : TodoListAdapter
    lateinit var binding : ActivityAddTodoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as CustomApplication).component.activityComponent.create(this).inject(this)
        init()
        val foo = ActivityAddTodoBinding::class.java
    }

    private fun init() {
        viewmodel.beginAdd()
        binding.viewmodel = viewmodel
        binding.spinnerPriority.adapter = ArrayAdapter<TodoPriority>(this, R.layout.enum_spinner_item, TodoPriority.values())
    }
}
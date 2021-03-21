package com.test.propertymanagementapp.ui.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.propertymanagementapp.app.CustomApplication
import com.test.propertymanagementapp.databinding.ActivityTodoListBinding
import javax.inject.Inject

class TodoListActivity : AppCompatActivity() {
    lateinit var binding:ActivityTodoListBinding
    @Inject
    lateinit var viewmodel:TodoViewModel
    @Inject
    lateinit var adapter:TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as CustomApplication).component.activityComponent.create(this).inject(this)
        init()
    }

    private fun init() {
        binding.recyclerTodos.adapter = adapter
        binding.recyclerTodos.layoutManager = LinearLayoutManager(this)
    }
}
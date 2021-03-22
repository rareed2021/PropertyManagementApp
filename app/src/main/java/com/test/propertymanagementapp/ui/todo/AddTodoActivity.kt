package com.test.propertymanagementapp.ui.todo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.test.propertymanagementapp.R
import com.test.propertymanagementapp.app.CustomApplication
import com.test.propertymanagementapp.data.models.Todo
import com.test.propertymanagementapp.data.models.enums.TodoPriority
import com.test.propertymanagementapp.data.models.enums.TodoStatus
import com.test.propertymanagementapp.databinding.ActivityAddTodoBinding
import com.test.propertymanagementapp.databinding.ActivityTodoListBinding
import com.test.propertymanagementapp.ui.common.BaseViewModel
import com.test.propertymanagementapp.ui.common.ListType
import com.test.propertymanagementapp.ui.properties.PropertyHomeActivity
import com.test.propertymanagementapp.ui.properties.PropertyViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

class AddTodoActivity : AppCompatActivity() {
    @Inject
    lateinit var viewmodel: TodoViewModel
    @Inject
    lateinit var propertyViewModel: PropertyViewModel

    @Inject
    lateinit var adapter: TodoListAdapter
    lateinit var binding: ActivityAddTodoBinding
    lateinit var spinner: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as CustomApplication).component.activityComponent.create(this).inject(this)
        init()
        val foo = ActivityAddTodoBinding::class.java
    }

    private fun init() {
        val todo = intent.getSerializableExtra(Todo.KEY) as? Todo
        if(todo!=null){
            viewmodel.beginEdit(todo)
        }else{
            viewmodel.beginAdd()
        }
        binding.lifecycleOwner
        binding.viewmodel = viewmodel
        binding.spinnerPriority.adapter =
            ArrayAdapter(this, R.layout.enum_spinner_item, TodoPriority.values())
        binding.spinnerStatus.adapter =
            ArrayAdapter(this, R.layout.enum_spinner_item, TodoStatus.values())
        binding.areaDate.setOnClickListener {
            val dialog = DatePickerDialog(this)
            val current = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(
                    viewmodel.current.value?.dueDate ?: Instant.now().toEpochMilli()
                ),
                ZoneOffset.UTC
            )
            current.apply { dialog.updateDate(year,monthValue-1,dayOfMonth) }
            dialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                val dt = LocalDateTime.of(year, month+1, dayOfMonth, 0, 0, 0, 0)
                val inst = dt.toInstant(ZoneOffset.UTC).toEpochMilli()
                viewmodel.current.value?.dueDate = inst
                binding.textDueDate.text = viewmodel.current.value?.dueDateString ?: ""
            }
            dialog.show()
        }
        propertyViewModel.setupForSelecting()
        propertyViewModel._state.observe(this){
            if(it == BaseViewModel.State.FINISHED){
                val prop = propertyViewModel.current.value?.id
                if(prop!=null) {
                    viewmodel.current.value?.propertyId = prop
                }
            }
        }
        binding.areaProperty.setOnClickListener {
            val properties = propertyViewModel.properties.value
            val propertyNames = properties?.map { p->p.address?:"" }?.toTypedArray()
            if(propertyNames!=null) {
                val selected = properties.indexOf(viewmodel.current.value?.propertyId?.let{id->  properties.find { it.id ==id }})
                val dialog = AlertDialog.Builder(this)
                    .setItems(propertyNames) { dialog, which ->
                        binding.textProperty.text = properties[which].address
                        viewmodel.current.value?.propertyId = properties[which].id
                        viewmodel.current.value?.propertyAddress = properties[which].address
                        dialog.dismiss()
                    }
                    .show()
            }
        }
        viewmodel._state.observe(this){
            if(it==BaseViewModel.State.FINISHED)
                finish()
        }
    }
}
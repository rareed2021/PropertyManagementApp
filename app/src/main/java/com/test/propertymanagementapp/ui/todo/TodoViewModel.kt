package com.test.propertymanagementapp.ui.todo

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.test.propertymanagementapp.data.models.Todo
import com.test.propertymanagementapp.data.models.TodoComparator
import com.test.propertymanagementapp.data.repositories.AuthRepository
import com.test.propertymanagementapp.data.repositories.TodoRepository
import com.test.propertymanagementapp.ui.common.EditType
import com.test.propertymanagementapp.ui.common.ListViewModel
import com.test.propertymanagementapp.ui.common.logAll
import kotlinx.coroutines.launch

class TodoViewModel(val repository: TodoRepository, val auth: AuthRepository, val validator:TodoValidator) :
    ListViewModel<Todo>(Todo::create) {
    val todoList = MutableLiveData<List<Todo>>()

    init {
        //set up data listener
        viewModelScope.launch {
            val user = auth.getUser()
            user?.also { u ->
                val ref = repository.watchTodos(u._id)
                ref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val newData = mutableListOf<Todo>()
                        for (todo in snapshot.children) {
                            val item = todo.getValue(Todo::class.java)
                            item?.also { newData.add(it) }
                        }
                        newData.sortWith(TodoComparator())
                        todoList.value = newData
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("myapp", error.message)
                        Log.e("myapp", error.details)
                    }
                })
            }
        }
    }


    fun buttonClick(view: View){
        viewModelScope.launch {
            val addTodo = current.value
            val result = validator.validateTodo(addTodo)
            val type =editType.value
            if (result.isEmpty()) {
                val user = auth.getUser()
                if(user!=null) {
                    Log.d("myapp", "adding ${current.value}")
                    //validator checks for null
                    when(type){
                        EditType.ADD->repository.addTodo(user._id, addTodo!!)
                        EditType.EDIT->repository.updateTodo(user._id, addTodo!!)
                    }

                    _state.value = State.FINISHED
                }
            }else{
                _error.value = result.first().message
                result.logAll("add todo")
            }
        }
    }
}
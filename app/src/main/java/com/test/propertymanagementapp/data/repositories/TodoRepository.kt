package com.test.propertymanagementapp.data.repositories

import android.util.Log
import com.google.firebase.database.*
import com.test.propertymanagementapp.data.models.Todo
import com.test.propertymanagementapp.data.models.enums.TodoPriority
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TodoRepository @Inject constructor(val db:FirebaseDatabase) {
    private fun getTodoRef(id:String):DatabaseReference{
        return db.reference.child("users/$id/todos")
    }

    fun addTodo(userId:String, todo:Todo){
        val todoRef = getTodoRef(userId).push()
        todo.id = todoRef.key
        todoRef.setValue(todo)
    }

    fun updateTodo(userId:String, todo: Todo){
        val todoRef = getTodoRef(userId)
        val id = todo.id
        if(id!=null)
            todoRef.child(id).setValue(todo)
    }
    fun watchTodos(userId:String):DatabaseReference{
        return getTodoRef(userId)
    }
    suspend fun getTodos(userId:String):List<Todo>{
        return suspendCoroutine {continuation->
            getTodoRef(userId).addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val ret = mutableListOf<Todo>()
                    for(todo in snapshot.children){
                        val item = todo.getValue(Todo::class.java)
                        item?.also{ret.add(it)}
                    }
                    continuation.resume(ret)
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("myapp",error.message)
                }
            })
        }
    }
}
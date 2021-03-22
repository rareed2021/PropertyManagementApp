package com.test.propertymanagementapp.ui.todo

import com.test.propertymanagementapp.data.models.Todo
import java.util.*
import javax.inject.Inject
import com.test.propertymanagementapp.ui.todo.TodoValidationError.*

class TodoValidator @Inject constructor(){

    fun validateTodo(todo: Todo?):EnumSet<TodoValidationError>{
        val ret = EnumSet.noneOf(TodoValidationError::class.java)
        if(todo!=null){
            if(todo.description.isNullOrBlank())
                ret.add(NoDescription)
            if(todo.summary.isNullOrBlank())
                ret.add(NoSummary)
        }else{
            ret.add(NullTodo)
        }
        return ret
    }
}



enum class TodoValidationError(val message:String){
    NullTodo("Todo was null"),
    NoDescription("Please provide a description"),
    NoSummary("Please provide a summary")

}
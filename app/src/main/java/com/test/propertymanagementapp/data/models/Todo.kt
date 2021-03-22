package com.test.propertymanagementapp.data.models

import com.google.firebase.database.Exclude
import com.test.propertymanagementapp.data.models.enums.TodoPriority
import com.test.propertymanagementapp.data.models.enums.TodoStatus
import java.io.Serializable
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class Todo(
    var id:String?=null,
    var priority: TodoPriority = TodoPriority.Low,
    var summary: String = "",
    var description: String = "",
    var propertyId: String? = null,
    var propertyAddress : String?=null,
    var estimatedCost: String = "",
    var dueDate: Long = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli(),
    var actualCost: String = "",
    var status: TodoStatus = TodoStatus.Pending
) :Serializable{
    val dueDateString: String
        @get:Exclude
        get() {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(dueDate), ZoneId.of("UTC"))
                .format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
        }
    companion object{
        fun create()=  Todo()
        const val KEY="TODO"
    }
}


//compares two Todos
//sorts in order of status, then priority, then due date
class TodoComparator : Comparator<Todo>{
    override fun compare(first: Todo?, second: Todo?): Int {
        var ret = 0
        if(first!=null){
            if(second!=null){
                ret = first.status.compareTo(second.status)
                if(ret ==0){
                    ret = first.priority.compareTo(second.priority)
                }
                if(ret == 0){
                    ret = first.dueDate.compareTo(second.dueDate)
                }
            }else{
                ret= -1
            }
        }else {
            ret = if (second==null)0 else 1
        }
        return ret

    }

}

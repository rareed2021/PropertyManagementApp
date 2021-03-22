package com.test.propertymanagementapp.ui.todo

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.test.propertymanagementapp.data.models.Todo
import com.test.propertymanagementapp.databinding.RowTodoItemBinding
import javax.inject.Inject

class TodoListAdapter @Inject constructor(val activity:AppCompatActivity, val viewmodel:TodoViewModel) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {
    val mData = mutableListOf<Todo>()

    init {
        viewmodel.todoList.observe(activity){
            mData.clear()
            mData.addAll(it)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(val binding:RowTodoItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.root.setOnClickListener {
                val intent = Intent(activity, AddTodoActivity::class.java)
                intent.putExtra(Todo.KEY,todo)
                activity.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowTodoItemBinding.inflate(LayoutInflater.from(activity), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }

}
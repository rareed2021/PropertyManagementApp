package com.test.propertymanagementapp.ui.home

import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.test.propertymanagementapp.databinding.RowIconBinding
import javax.inject.Inject

class ActionButtonsAdapter @Inject constructor(val activity:AppCompatActivity, val viewmodel : HomeViewModel)
    :RecyclerView.Adapter<ActionButtonsAdapter.ViewHolder>(){
    val mData = mutableListOf<ActionIcon>()
    init {
        viewmodel.actions.observe(activity){
            Log.d("myapp","Possible activities changed")
            mData.clear()
            mData.addAll(it)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(val binding: RowIconBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowIconBinding.inflate(activity.layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.def = mData[position]
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}
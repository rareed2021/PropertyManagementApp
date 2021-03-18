package com.test.propertymanagementapp.ui.properties

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.test.propertymanagementapp.data.models.Property
import com.test.propertymanagementapp.databinding.RowPropertyBinding
import javax.inject.Inject

class PropertyAdapter @Inject constructor(val activity:AppCompatActivity, val viewmodel:PropertyViewModel)
    :RecyclerView.Adapter<PropertyAdapter.ViewHolder>(){
    val mData = mutableListOf<Property>()
    init{
    }
    inner class ViewHolder(val binding:RowPropertyBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowPropertyBinding.inflate(activity.layoutInflater, parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.property = mData[position]
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}
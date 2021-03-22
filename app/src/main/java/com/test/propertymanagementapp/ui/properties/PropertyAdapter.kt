package com.test.propertymanagementapp.ui.properties

import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.test.propertymanagementapp.data.models.Property
import com.test.propertymanagementapp.databinding.RowPropertyBinding
import com.test.propertymanagementapp.ui.common.ListType
import javax.inject.Inject

class PropertyAdapter @Inject constructor(
    val activity: AppCompatActivity,
    val viewmodel: PropertyViewModel
) : RecyclerView.Adapter<PropertyAdapter.ViewHolder>() {
    val mData = mutableListOf<Property>()

    init {
        viewmodel.properties.observe(activity) {
            mData.clear()
            mData.addAll(it)
            notifyDataSetChanged()
            Log.d("myapp", "Loading properties. ${it.size}")
        }
    }

    inner class ViewHolder(val binding: RowPropertyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(property: Property) {
            val listType = viewmodel.listType.value
            binding.root.setOnClickListener {
                val intent = Intent(activity, AddPropertyActivity::class.java)
                intent.putExtra(Property.KEY, property)
                activity.startActivity(intent)
            }
//            if (listType == ListType.SELECT) {
//                binding.root.setOnClickListener {
//                    viewmodel.selectItem(property)
//                }
//            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowPropertyBinding.inflate(activity.layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.property = mData[position]
        holder.bind(mData[position])
        Log.d("myapp", "Binding property :${mData[position]}")
    }

    override fun getItemCount(): Int {
        Log.d("myapp", "${mData.size}")
        return mData.size
    }
}
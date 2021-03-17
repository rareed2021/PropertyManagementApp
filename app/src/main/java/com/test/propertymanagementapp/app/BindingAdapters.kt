package com.test.propertymanagementapp.app

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:customVisibility")
fun setVisibility(view: View, visible:Boolean){
    view.visibility = if(visible) View.VISIBLE else View.GONE
}
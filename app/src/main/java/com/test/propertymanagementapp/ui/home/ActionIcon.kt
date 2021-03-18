package com.test.propertymanagementapp.ui.home

import android.view.View

class ActionIcon(
    val icon:Int,
    val title:String,
    val action:(()->Unit)?=null,
) {
    fun clickFunction(view: View){
        action?.invoke()
    }
}
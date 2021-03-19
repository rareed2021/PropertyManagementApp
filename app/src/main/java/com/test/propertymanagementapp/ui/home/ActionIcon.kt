package com.test.propertymanagementapp.ui.home

import android.app.Activity
import android.view.View
import kotlin.reflect.KClass

class ActionIcon(
    val icon:Int,
    val title:String,
    val nextActivity: Class<out Activity>?=null,
    val action:(()->Unit)?=null,
) {
    fun clickFunction(view: View){
        action?.invoke()
    }
}
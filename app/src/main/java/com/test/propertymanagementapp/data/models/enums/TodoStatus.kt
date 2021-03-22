package com.test.propertymanagementapp.data.models.enums

import android.graphics.Color
import com.test.propertymanagementapp.R

enum class TodoStatus(val colorRes:Int, val tint:Color) {
    Pending(R.color.pending, Color.valueOf(1.0f, 1.0f,1.0f,1.0f)),
    Completed(R.color.complete, Color.valueOf(0.8f, 0.8f,0.8f,1.0f)),
    Cancelled(R.color.cancelled, Color.valueOf(0.8f, 0.8f,0.8f,1.0f))
}
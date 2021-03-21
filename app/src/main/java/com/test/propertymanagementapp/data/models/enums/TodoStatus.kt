package com.test.propertymanagementapp.data.models.enums

import com.test.propertymanagementapp.R

enum class TodoStatus(val colorRes:Int) {
    Pending(R.color.pending),
    Completed(R.color.complete),
    Cancelled(R.color.cancelled)
}
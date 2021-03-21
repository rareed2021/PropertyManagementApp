package com.test.propertymanagementapp.ui.common

import androidx.lifecycle.MutableLiveData

abstract class ListViewModel<T>(private val creator: ()->T) : BaseViewModel() {
    val listType = MutableLiveData<ListType>().apply{value = ListType.EDIT }
    val editType = MutableLiveData<EditType>().apply { value = EditType.NONE }
    val current = MutableLiveData<T>()

    open fun setupForEditing(){
        listType.value = ListType.EDIT
    }

    open fun setupForSelecting(){
        listType.value = ListType.SELECT
    }

    open fun beginEdit(editItem:T){
        _state.value = State.INITIALIZED
        editType.value = EditType.EDIT
        if(editItem !=null){
            //it is demanding unsafe cast despite check. I don't know why
            current.value = editItem!!
        }
    }

    open fun beginAdd(){
        _state.value = State.INITIALIZED
        editType.value = EditType.ADD
        current.value = creator()
    }


}
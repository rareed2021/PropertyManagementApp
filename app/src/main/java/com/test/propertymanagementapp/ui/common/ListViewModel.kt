package com.test.propertymanagementapp.ui.common

import android.util.Log
import androidx.lifecycle.MutableLiveData

abstract class ListViewModel<T>(private val creator: ()->T) : BaseViewModel() {
    val listType = MutableLiveData<ListType>()
    val editType = MutableLiveData<EditType>()
    val current = MutableLiveData<T>()

    open fun setupForEditing(){
        listType.value = ListType.EDIT
        Log.d("myapp","Called setup editing")
    }

    open fun setupForSelecting(){
        listType.value = ListType.SELECT
        Log.d("myapp","Called setup selecting")
    }

    open fun beginEdit(editItem:T){
        _state.value = State.INITIALIZED
        editType.value = EditType.EDIT
        if(editItem !=null){
            //it is demanding unsafe cast despite check. I don't know why
            current.value = editItem!!
        }
    }

    open fun selectItem(selectedItem:T?){
        Log.d("myapp","Selecting $selectedItem")
        if(selectedItem!=null){
            current.value=selectedItem!!
            _state.value = State.FINISHED
            listType.value = ListType.EDIT
        }
    }

    open fun beginAdd(){
        _state.value = State.INITIALIZED
        editType.value = EditType.ADD
        current.value = creator()
    }

}
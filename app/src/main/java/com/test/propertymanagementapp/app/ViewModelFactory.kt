package com.test.propertymanagementapp.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass


@JvmSuppressWildcards
class ViewModelFactory @Inject constructor(val providers: Map<Class<*>, Provider<ViewModel>>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return providers[modelClass]?.get() as T
    }
}
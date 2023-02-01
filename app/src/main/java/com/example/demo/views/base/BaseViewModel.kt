package com.example.demo.views.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val eventShowLoading: MutableLiveData<Boolean?> by lazy {
        MutableLiveData<Boolean?>()
    }

    protected val eventShowMessage: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }
}
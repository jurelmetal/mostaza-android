package com.juanetoh.mostaza.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.readOnly(): LiveData<T> = this
package com.vsr2.mobile.facts.viewmodels.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    // Loading indicator
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
}
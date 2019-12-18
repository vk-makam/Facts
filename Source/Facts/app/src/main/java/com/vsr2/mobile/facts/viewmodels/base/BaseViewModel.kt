package com.vsr2.mobile.facts.viewmodels.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vsr2.mobile.facts.utils.APP_NAME

open class BaseViewModel : ViewModel() {

    // Loading indicator
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    // Title
    var title: MutableLiveData<String> = MutableLiveData(APP_NAME)
}
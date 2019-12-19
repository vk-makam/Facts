package com.vsr2.mobile.facts.viewmodels.home

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vsr2.mobile.facts.models.FactsResponse
import com.vsr2.mobile.facts.network.FactService
import com.vsr2.mobile.facts.network.RetrofitHelper
import com.vsr2.mobile.facts.utils.APP_NAME
import com.vsr2.mobile.facts.viewmodels.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : BaseViewModel() {

    // Facts MutableLiveData
    lateinit var facts: MutableLiveData<FactsResponse>

    // No data layout
    var noDataLayoutVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)

    init {

        if (!::facts.isInitialized) {
            facts = MutableLiveData()
            refreshFacts()
        }
    }

    fun refreshFacts() {

        viewModelScope.launch {
            fetchFacts()
        }
    }

    private suspend fun fetchFacts() {

        isLoading.postValue(true)

        withContext(Dispatchers.IO) {

            try {

                val service = RetrofitHelper.getInstance().createService(FactService::class.java)
                val call = service.getFacts()
                val response = call.execute()

                if (response.isSuccessful) {
                    facts.postValue(response.body())
                } else {
                    isLoading.postValue(false)
                }
            } catch (e: Exception) {
                Log.e(APP_NAME, e.message!!)
                noDataLayoutVisibility.postValue(View.VISIBLE)
            }
        }

        isLoading.postValue(false)
    }
}
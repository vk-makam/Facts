package com.vsr2.mobile.facts.viewmodels.home

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.vsr2.mobile.facts.models.FactsResponse
import com.vsr2.mobile.facts.network.FactService
import com.vsr2.mobile.facts.network.RetrofitHelper
import com.vsr2.mobile.facts.utils.APP_NAME
import com.vsr2.mobile.facts.viewmodels.base.BaseViewModel

class HomeViewModel : BaseViewModel() {

    // Facts MutableLiveData
    lateinit var facts: MutableLiveData<FactsResponse>

    // TODO: Initialize with sample data, till we implement network calls
    init {

        if (!::facts.isInitialized) {
            facts = MutableLiveData()
            refreshFacts()
        }
    }

    private fun refreshFacts() {
        MakeRequest().execute()
    }

    inner class MakeRequest : AsyncTask<Void, Void, FactsResponse>() {

        override fun onPreExecute() {
            super.onPreExecute()
            isLoading.postValue(true)
        }

        override fun doInBackground(vararg p0: Void?): FactsResponse? {
            // TODO: We can call FactRepository method here, to avoid unnecessary API calls
            return getFacts()
        }

        override fun onPostExecute(result: FactsResponse?) {
            super.onPostExecute(result)
            facts.postValue(result)
            isLoading.postValue(false)
        }

        override fun onCancelled(facts: FactsResponse?) {
            super.onCancelled()
            isLoading.postValue(false)
        }

        private fun getFacts(): FactsResponse? {
            var facts: FactsResponse? = null

            try {

                val service = RetrofitHelper.getInstance().createService(FactService::class.java)
                val call = service.getFacts()
                val response = call.execute()

                if (response.isSuccessful) {
                    facts = response.body()
                }
                // TODO: Handle else case and update the user
            } catch (e: Exception) {
                Log.e(APP_NAME, e.message!!)
                onCancelled(facts)
            }

            return facts
        }
    }
}
package com.vsr2.mobile.facts.viewmodels.home

import android.os.AsyncTask
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.vsr2.mobile.facts.models.FactsResponse
import com.vsr2.mobile.facts.network.FactService
import com.vsr2.mobile.facts.network.RetrofitHelper
import com.vsr2.mobile.facts.utils.APP_NAME
import com.vsr2.mobile.facts.viewmodels.base.BaseViewModel

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
        MakeRequest().execute()
    }

    inner class MakeRequest : AsyncTask<Void, Void, FactsResponse>() {

        override fun onPreExecute() {
            super.onPreExecute()
            isLoading.postValue(true)
        }

        override fun doInBackground(vararg p0: Void?): FactsResponse? {

            // TODO: We can implement FactRepository method here, to avoid unnecessary API calls
            return getFacts()
        }

        override fun onPostExecute(result: FactsResponse?) {
            super.onPostExecute(result)
            isLoading.postValue(false)

            if (null == result) {
                noDataLayoutVisibility.postValue(View.VISIBLE)
            } else {
                facts.postValue(result)
            }
        }

        override fun onCancelled(facts: FactsResponse?) {
            super.onCancelled()
            isLoading.postValue(false)

            if (null == facts) {
                noDataLayoutVisibility.postValue(View.VISIBLE)
            }
        }

        private fun getFacts(): FactsResponse? {
            var facts: FactsResponse? = null

            // TODO: Yet to check for internet connectivity, before hitting the server
            // Time being its handle in catch block

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
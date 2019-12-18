package com.vsr2.mobile.facts.network

import com.vsr2.mobile.facts.utils.FACTS_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    private val builder: Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(FACTS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    private val _retrofit: Retrofit = builder.build()

    fun <S> createService(serviceClass: Class<S>): S {
        return _retrofit.create(serviceClass)
    }

    companion object {

        @Volatile
        private var instance: RetrofitHelper? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: RetrofitHelper().also { instance = it }
            }
    }
}

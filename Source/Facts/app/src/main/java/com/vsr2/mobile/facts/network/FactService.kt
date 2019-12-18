package com.vsr2.mobile.facts.network

import com.vsr2.mobile.facts.models.FactsResponse
import retrofit2.http.GET

interface FactService {

    @GET("/s/2iodh4vg0eortkl/facts.json")
    fun getFacts(): retrofit2.Call<FactsResponse>
}
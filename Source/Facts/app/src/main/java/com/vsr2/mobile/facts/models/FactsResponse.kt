package com.vsr2.mobile.facts.models

data class FactsResponse(
    val title: String,
    val rows: List<Fact>
)
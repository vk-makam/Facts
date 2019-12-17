package com.vsr2.mobile.facts.viewmodels.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vsr2.mobile.facts.models.Fact
import com.vsr2.mobile.facts.viewmodels.base.BaseViewModel

class HomeViewModel : BaseViewModel() {

    private val _sampleFacts = listOf(
        Fact(
            title = "Beavers",
            description = "Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony",
            imageUrl = "http://images.findicons.com/files/icons/662/world_flag/128/flag_of_canada.png"
        ),
        Fact(
            title = "Flag",
            description = null,
            imageUrl = "http://images.findicons.com/files/icons/662/world_flag/128/flag_of_canada.png"
        ),
        Fact(
            title = "Transportation",
            description = "It is a well known fact that polar bears are the main mode of transportation in Canada. They consume far less gas and have the added benefit of being difficult to steal.",
            imageUrl = "http://1.bp.blogspot.com/_VZVOmYVm68Q/SMkzZzkGXKI/AAAAAAAAADQ/U89miaCkcyo/s400/the_golden_compass_still.jpg"
        ),
        Fact(
            title = "Hockey Night in Canada",
            description = "These Saturday night CBC broadcasts originally aired on radio in 1931. In 1952 they debuted on television and continue to unite (and divide) the nation each week.",
            imageUrl = "http://fyimusic.ca/wp-content/uploads/2008/06/hockey-night-in-canada.thumbnail.jpg"
        ),
        Fact(
            title = "Eh",
            description = "A chiefly Canadian interrogative utterance, usually expressing surprise or doubt or seeking confirmation.",
            imageUrl = null
        ),
        Fact(
            title = "Housing",
            description = "Warmer than you might think.",
            imageUrl = "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png"
        ),
        Fact(
            title = null,
            description = null,
            imageUrl = null
        )
    )

    // Facts MutableLiveData
    private val _facts = MutableLiveData<List<Fact>>()
    val facts: LiveData<List<Fact>>
        get() = _facts

    // TODO: Initialize with sample data, till we implement network calls
    init {
        _facts.postValue(_sampleFacts)
    }
}
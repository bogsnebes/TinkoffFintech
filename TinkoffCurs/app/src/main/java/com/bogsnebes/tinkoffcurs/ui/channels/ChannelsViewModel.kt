package com.bogsnebes.tinkoffcurs.ui.channels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bogsnebes.tinkoffcurs.data.dto.StreamDto
import com.bogsnebes.tinkoffcurs.data.dto.TestData

class ChannelsViewModel : ViewModel() {
    private val allStreams = MutableLiveData<MutableList<StreamDto>>()
    var resultStreams = MutableLiveData<MutableList<StreamDto>>()

    init {
        allStreams.value = TestData.testStreams
        resultStreams.value = allStreams.value
    }

    fun loadCacheData() {
        resultStreams.postValue(resultStreams.value)
    }

    fun filter(text: String) {
        allStreams.value?.let {
            for (item in it) {
                resultStreams.value = mutableListOf()
                if (item.category.lowercase().contains(text.lowercase())) {
                    resultStreams.value!!.add(item)
                }
            }
        }
    }
}
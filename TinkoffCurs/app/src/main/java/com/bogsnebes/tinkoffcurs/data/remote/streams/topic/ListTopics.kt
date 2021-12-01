package com.bogsnebes.tinkoffcurs.data.remote.streams.topic

import com.bogsnebes.tinkoffcurs.data.dto.Topic
import com.google.gson.annotations.SerializedName

data class ListTopics(
    @SerializedName("result")
    val result: String,
    @SerializedName("topics")
    val topics: List<Topic>
)
package com.bogsnebes.tinkoffcurs.data.remote.streams.stream

import com.bogsnebes.tinkoffcurs.data.remote.streams.topic.Topic
import com.google.gson.annotations.SerializedName

data class Stream(
    @SerializedName("name")
    val category: String,
    @SerializedName("stream_id")
    val streamId: Int,
)

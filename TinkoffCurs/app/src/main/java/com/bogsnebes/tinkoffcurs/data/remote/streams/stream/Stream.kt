package com.bogsnebes.tinkoffcurs.data.remote.streams.stream

import com.google.gson.annotations.SerializedName

data class Stream(
    @SerializedName("name")
    val name: String,
    @SerializedName("stream_id")
    val streamId: Int,
)

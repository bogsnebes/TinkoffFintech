package com.bogsnebes.tinkoffcurs.data.remote.streams.stream

import com.google.gson.annotations.SerializedName

data class ListStreams(
    @SerializedName("result")
    val result: String,
    @SerializedName("streams")
    val streams: List<Stream>
)

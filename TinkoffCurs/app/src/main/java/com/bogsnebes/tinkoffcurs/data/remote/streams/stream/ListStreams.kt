package com.bogsnebes.tinkoffcurs.data.remote.streams.stream

import com.bogsnebes.tinkoffcurs.data.dto.Stream
import com.google.gson.annotations.SerializedName

data class ListStreams(
    @SerializedName("result")
    val result: String,
    @SerializedName("streams")
    val streams: List<Stream>
)

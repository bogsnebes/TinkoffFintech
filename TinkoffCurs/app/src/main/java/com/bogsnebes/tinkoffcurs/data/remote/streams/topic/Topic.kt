package com.bogsnebes.tinkoffcurs.data.remote.streams.topic

import com.google.gson.annotations.SerializedName

data class Topic(
    @SerializedName("max_id")
    val topicId: Int,
    @SerializedName("name")
    val name: String
)

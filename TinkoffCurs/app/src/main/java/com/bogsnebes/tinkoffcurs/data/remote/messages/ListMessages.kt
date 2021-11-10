package com.bogsnebes.tinkoffcurs.data.remote.messages

import com.google.gson.annotations.SerializedName

data class ListMessages(
    @SerializedName("result")
    val result: String,
    @SerializedName("found_newest")
    val foundNewest: Boolean,
    @SerializedName("anchor")
    val anchor: Int,
    @SerializedName("messages")
    val messages: List<Message>
)
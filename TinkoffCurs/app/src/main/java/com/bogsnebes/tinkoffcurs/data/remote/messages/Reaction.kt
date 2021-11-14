package com.bogsnebes.tinkoffcurs.data.remote.messages

import com.google.gson.annotations.SerializedName

data class Reaction(
    @SerializedName("emoji_code")
    val emojiCode: String,
    @SerializedName("user_id")
    val userId: Int
)

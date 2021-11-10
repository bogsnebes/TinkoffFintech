package com.bogsnebes.tinkoffcurs.data.remote.users

import com.google.gson.annotations.SerializedName

data class UserPresence(
    @SerializedName("result")
    val result: String,
)

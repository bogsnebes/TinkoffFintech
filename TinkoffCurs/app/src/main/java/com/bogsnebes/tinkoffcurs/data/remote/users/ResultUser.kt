package com.bogsnebes.tinkoffcurs.data.remote.users

import com.google.gson.annotations.SerializedName

data class ResultUser(
    @SerializedName("result")
    val result: String,
    @SerializedName("user")
    val user: User
)

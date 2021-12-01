package com.bogsnebes.tinkoffcurs.data.remote.users

import com.bogsnebes.tinkoffcurs.data.dto.User
import com.google.gson.annotations.SerializedName

data class ListUsers(
    @SerializedName("result")
    val result: String,
    @SerializedName("members")
    val users: List<User>,
)

package com.bogsnebes.tinkoffcurs.data.remote.users

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String
)

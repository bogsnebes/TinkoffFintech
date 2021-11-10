package com.bogsnebes.tinkoffcurs.data.remote.users

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("avatar_url")
    val avatarUrl: Int,
    @SerializedName("email")
    val email: Int,
    @SerializedName("full_name")
    val fullName: Int
)

package com.bogsnebes.tinkoffcurs.data.remote

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("result")
    val result: String
)

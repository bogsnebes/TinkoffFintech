package com.bogsnebes.tinkoffcurs.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String
)

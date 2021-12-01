package com.bogsnebes.tinkoffcurs.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "streams")
data class Stream(
    @SerializedName("name")
    val name: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("stream_id")
    val streamId: Int,
)

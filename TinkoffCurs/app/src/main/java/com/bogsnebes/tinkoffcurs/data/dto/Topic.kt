package com.bogsnebes.tinkoffcurs.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "topics")
data class Topic(
    @SerializedName("max_id")
    val maxMessageId: Int,
    @SerializedName("name")
    val name: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long?
)

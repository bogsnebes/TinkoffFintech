package com.bogsnebes.tinkoffcurs.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bogsnebes.tinkoffcurs.data.remote.messages.Reaction
import com.google.gson.annotations.SerializedName

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val messageId: Long,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("content")
    val message: String,
    @SerializedName("content_type")
    val content_type: String,
    @SerializedName("is_me_message")
    val isMeMessage: Boolean,
    @SerializedName("sender_full_name")
    val senderName: String,
    @SerializedName("sender_id")
    val senderId: Int,
    @SerializedName("timestamp")
    val timestamp: Int,
    @SerializedName("reactions")
    val reactions: List<Reaction>
)

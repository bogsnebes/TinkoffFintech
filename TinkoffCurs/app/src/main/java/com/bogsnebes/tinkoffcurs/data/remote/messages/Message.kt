package com.bogsnebes.tinkoffcurs.data.remote.messages

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("id")
    val messageId: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("content")
    val message: String,
    @SerializedName("content_type")
    val content_type: String,
    @SerializedName("is_me_message")
    val isMeMessage: Boolean,
    @SerializedName("sender_full_name")
    val senderName: Int,
    @SerializedName("sender_id")
    val senderId: Int,
    @SerializedName("timestamp")
    val timestamp: Int,
    @SerializedName("reactions")
    val reactions: List<Reaction>
)

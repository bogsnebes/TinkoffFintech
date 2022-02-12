package com.bogsnebes.tinkoffcurs.ui.chat.recycler

data class MessageItem(
    val id: Long,
    val userId: Int,
    val sender: String,
    val message: String,
    val profileImage: String?,
    val reactions: List<ReactionItem>,
    val date: String
)

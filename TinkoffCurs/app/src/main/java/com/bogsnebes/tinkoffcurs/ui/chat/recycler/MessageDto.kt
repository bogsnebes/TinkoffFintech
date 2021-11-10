package com.bogsnebes.tinkoffcurs.ui.chat.recycler

data class MessageDto(
    val id: Int,
    val userId: Int,
    val sender: String,
    val message: String,
    val profileImage: String?,
    val reactions: List<ReactionDto>,
    val date: String
)

package com.bogsnebes.tinkoffcurs.data.dto

data class MessageDto(
    val id: Int,
    val userId: Int,
    val sender: String,
    val message: String,
    val profileImage: String?,
    val reactions: List<ReactionDto>,
    val date: String
)

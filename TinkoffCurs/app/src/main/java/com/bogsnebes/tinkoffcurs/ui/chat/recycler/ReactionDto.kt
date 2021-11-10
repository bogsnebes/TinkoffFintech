package com.bogsnebes.tinkoffcurs.ui.chat.recycler

data class ReactionDto(
    val userId: Int,
    val emoji: String,
    val countReactions: Int
)
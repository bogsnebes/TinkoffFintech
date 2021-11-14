package com.bogsnebes.tinkoffcurs.ui.chat.recycler

data class ReactionItem(
    val userId: Int,
    val emoji: String,
    val countReactions: Int
)
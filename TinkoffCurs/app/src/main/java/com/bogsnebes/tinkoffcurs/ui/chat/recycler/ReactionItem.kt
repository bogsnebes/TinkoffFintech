package com.bogsnebes.tinkoffcurs.ui.chat.recycler

data class ReactionItem(
    val selectedUser: Boolean,
    val emoji: String,
    val emoji_code: String,
    var countReactions: Int
)
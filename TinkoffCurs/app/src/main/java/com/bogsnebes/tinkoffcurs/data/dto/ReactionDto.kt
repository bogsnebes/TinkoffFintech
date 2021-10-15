package com.bogsnebes.tinkoffcurs.data.dto

data class ReactionDto(
    val userId: Int,
    val emoji: String,
    val countReactions: Int
)
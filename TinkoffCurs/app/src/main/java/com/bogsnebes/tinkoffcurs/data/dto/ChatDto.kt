package com.bogsnebes.tinkoffcurs.data.dto

import java.io.Serializable

data class ChatDto(
    val name: String,
    val countMessages: Int,
    val messages: MutableList<MessageDto>
): Serializable
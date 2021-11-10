package com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler

import com.bogsnebes.tinkoffcurs.ui.chat.recycler.MessageDto
import java.io.Serializable

data class ChatItem(
    val name: String,
    val countMessages: Int,
    val messages: MutableList<MessageDto>,
    var category: String = ""
) : Serializable
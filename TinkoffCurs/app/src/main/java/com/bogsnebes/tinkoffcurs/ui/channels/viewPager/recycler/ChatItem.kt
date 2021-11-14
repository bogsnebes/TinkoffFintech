package com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler

import com.bogsnebes.tinkoffcurs.ui.chat.recycler.MessageItem
import java.io.Serializable

data class ChatItem(
    val name: String,
    val countMessages: Int,
    val messages: MutableList<MessageItem>,
    var category: String = ""
) : Serializable
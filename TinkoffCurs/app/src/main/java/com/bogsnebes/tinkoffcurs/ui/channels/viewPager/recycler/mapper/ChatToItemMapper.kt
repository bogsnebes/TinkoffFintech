package com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler.mapper

import com.bogsnebes.tinkoffcurs.data.remote.streams.topic.Topic
import com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler.ChatItem

internal class ChatToItemMapper : (List<Topic>) -> (List<ChatItem>) {
    override fun invoke(topics: List<Topic>): List<ChatItem> {
        return topics.map { topic ->
            ChatItem(
                name = topic.name,
                countMessages = 0,
                messages = mutableListOf()
            )
        }
    }
}

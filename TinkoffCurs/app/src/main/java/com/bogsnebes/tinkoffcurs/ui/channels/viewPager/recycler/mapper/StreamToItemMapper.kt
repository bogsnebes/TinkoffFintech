package com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler.mapper

import com.bogsnebes.tinkoffcurs.data.remote.streams.stream.Stream
import com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler.StreamItem

internal class StreamToItemMapper : (List<Stream>) -> (List<StreamItem>) {
    override fun invoke(streams: List<Stream>): List<StreamItem> {
        return streams.map { stream ->
            StreamItem(
                category = stream.category,
                chats = listOf()
            )
        }
    }
}

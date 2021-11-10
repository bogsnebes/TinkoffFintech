package com.bogsnebes.tinkoffcurs.ui.channels.viewPager

import com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler.StreamItem

sealed class StreamsScreenState {
    class Result(val items: List<StreamItem>) : StreamsScreenState()

    object Loading : StreamsScreenState()

    class Error(val error: Throwable) : StreamsScreenState()
}
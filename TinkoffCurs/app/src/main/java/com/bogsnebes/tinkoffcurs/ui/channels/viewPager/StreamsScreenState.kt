package com.bogsnebes.tinkoffcurs.ui.channels.viewPager

import com.bogsnebes.tinkoffcurs.data.dto.StreamDto

sealed class StreamsScreenState {
    class Result(val items: List<StreamDto>) : StreamsScreenState()

    object Loading : StreamsScreenState()

    class Error(val error: Throwable) : StreamsScreenState()
}
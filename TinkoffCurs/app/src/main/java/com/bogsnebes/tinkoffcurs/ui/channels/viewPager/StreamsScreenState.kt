package com.bogsnebes.tinkoffcurs.ui.channels.viewPager

import com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler.StreamItem
import timber.log.Timber

sealed class StreamsScreenState {
    class Result(val items: List<StreamItem>) : StreamsScreenState()

    object Loading : StreamsScreenState()

    class Error(private val error: Throwable) : StreamsScreenState() {
        init {
            Timber.e(error)
        }
    }
}
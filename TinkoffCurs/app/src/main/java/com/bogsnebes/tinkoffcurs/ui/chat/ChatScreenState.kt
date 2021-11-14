package com.bogsnebes.tinkoffcurs.ui.chat

import com.bogsnebes.tinkoffcurs.ui.chat.recycler.MessageItem
import timber.log.Timber

sealed class ChatScreenState {
    class Result(val items: List<MessageItem>) : ChatScreenState()

    object Loading : ChatScreenState()

    class Error(private val error: Throwable) : ChatScreenState() {
        init {
            Timber.e(error)
        }
    }
}
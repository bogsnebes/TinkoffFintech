package com.bogsnebes.tinkoffcurs.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bogsnebes.tinkoffcurs.App.Companion.retrofit
import com.bogsnebes.tinkoffcurs.data.impl.MessagesImpl
import com.bogsnebes.tinkoffcurs.data.remote.messages.MessagesApi
import com.bogsnebes.tinkoffcurs.ui.chat.recycler.MessageItem
import com.bogsnebes.tinkoffcurs.ui.chat.recycler.ReactionItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ChatViewModel : ViewModel() {
    private var _chatScreenState: MutableLiveData<ChatScreenState> = MutableLiveData()
    val chatScreenState: LiveData<ChatScreenState>
        get() = _chatScreenState
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val messagesImpl = MessagesImpl()
    private val messagesApi = retrofit.create(MessagesApi::class.java)

    fun getMessages(topicName: String) {
        messagesImpl.loadMessages(MOCK_COUNT, MOCK_COUNT, topicName)
            .subscribeOn(Schedulers.io())
            .flatMapIterable { it }
            .map { message ->
                MessageItem(
                    id = message.messageId,
                    userId = message.senderId,
                    sender = message.senderName,
                    message = removeHtmlTag(message.message),
                    profileImage = message.avatarUrl,
                    date = "",
                    reactions = message.reactions.map {
                        ReactionItem(
                            userId = it.userId,
                            emoji = it.emojiCode,
                            countReactions = 1
                        )
                    }
                )
            }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _chatScreenState.value = ChatScreenState.Loading }
            .subscribeBy(
                onSuccess = { _chatScreenState.value = ChatScreenState.Result(it) },
                onError = { _chatScreenState.value = ChatScreenState.Error(it) }
            )
            .addTo(compositeDisposable)
    }

    private fun removeHtmlTag(htmlString: String): String {
        return htmlString.replace("<.*?>".toRegex(), "").trim { it <= ' ' }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    companion object {
        const val MOCK_COUNT = 1000
    }
}
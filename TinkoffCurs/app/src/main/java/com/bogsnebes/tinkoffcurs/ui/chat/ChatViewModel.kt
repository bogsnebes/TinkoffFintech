package com.bogsnebes.tinkoffcurs.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bogsnebes.tinkoffcurs.App.Companion.retrofit
import com.bogsnebes.tinkoffcurs.data.impl.MessagesImpl
import com.bogsnebes.tinkoffcurs.data.remote.messages.MessagesApi
import com.bogsnebes.tinkoffcurs.data.remote.messages.Reaction
import com.bogsnebes.tinkoffcurs.ui.chat.recycler.MessageAdapter
import com.bogsnebes.tinkoffcurs.ui.chat.recycler.MessageItem
import com.bogsnebes.tinkoffcurs.ui.chat.recycler.ReactionItem
import com.bogsnebes.tinkoffcurs.ui.custom.FlexBoxLayout
import com.bogsnebes.tinkoffcurs.ui.custom.reaction.ReactionAddViewButton
import com.bogsnebes.tinkoffcurs.ui.custom.reaction.ReactionButton
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
                    reactions = reactionsConverter(message.reactions)
                )
            }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { _chatScreenState.value = ChatScreenState.Result(it) },
                onError = { _chatScreenState.value = ChatScreenState.Error(it) }
            )
            .addTo(compositeDisposable)
    }

    fun sendMessage(
        streamName: String,
        topic: String,
        content: String,
    ) {
        messagesImpl.sendMessage(streamName, topic, content)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    if (it.result == "success") {
                        getMessages(topic)
                    } else {
                        _chatScreenState.value = ChatScreenState.SendError()
                    }
                },
                onError = { _chatScreenState.value = ChatScreenState.SendError(it) })
            .addTo(compositeDisposable)
    }

    fun clickReaction(
        messageId: Long,
        emoji: String,
        selected: Boolean,
        callbackRemoveReaction: () -> Unit,
        callbackAddReaction: () -> Unit
    ) {
        if (selected) {
            removeReaction(messageId, emoji) {
                callbackRemoveReaction()
            }
        } else {
            addReaction(messageId, emoji) {
                callbackAddReaction()
            }
        }
    }

    private fun removeReaction(messageId: Long, emoji: String, callbackReaction: () -> Unit) {
        messagesApi.removeReaction(messageId, emoji)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    if (it.result == "success") {
                        callbackReaction()
                    }
                },
                onError = { _chatScreenState.value = ChatScreenState.Error(it) }
            )
            .addTo(compositeDisposable)
    }

    fun addReaction(
        messageId: Long,
        emoji: String,
        holder: MessageAdapter.ViewHolder,
        reactionListener: (reactionButton: ReactionButton, flexBoxLayout: FlexBoxLayout) -> Unit,
        addReactionListener: (reactionAddViewButton: ReactionAddViewButton) -> Unit
    ) {
        messagesApi.addReaction(messageId, emoji)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    if (it.result == "success") {
                        holder.messageView.addReaction(emoji, 1, true)
                        holder.messageView.setOnReactionClickListener { reactionButton, flexBoxLayout ->
                            reactionListener(reactionButton, flexBoxLayout)
                        }
                        holder.messageView.setOnAddReactionClickListener { addReaction ->
                            addReactionListener(addReaction)
                        }
                    }
                },
                onError = { _chatScreenState.value = ChatScreenState.Error(it) })
            .addTo(compositeDisposable)
    }

    private fun addReaction(
        messageId: Long,
        emoji: String,
        callbackReaction: () -> Unit
    ) {
        messagesApi.addReaction(messageId, emoji)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    if (it.result == "success") {
                        callbackReaction()
                    }
                },
                onError = { _chatScreenState.value = ChatScreenState.Error(it) })
            .addTo(compositeDisposable)
    }

    private fun removeHtmlTag(htmlString: String): String {
        return htmlString.replace("<.*?>".toRegex(), "").trim { it <= ' ' }
    }

    private fun reactionsConverter(reactions: List<Reaction>): MutableList<ReactionItem> {
        val reactionsItem: MutableList<ReactionItem> = mutableListOf()
        var userId = false
        val groupBy = reactions.groupBy { it.emojiCode }
        groupBy?.let {
            for (key in groupBy.keys) {
                for (value in groupBy[key]!!) {
                    if (value.userId == ChatFragment.USER_ID)
                        userId = true
                }
                reactionsItem.add(
                    ReactionItem(
                        selectedUser = userId,
                        emoji = if (!key.contains("-") && !key.contains("zulip"))
                            key.toInt(16).toChar().toString() else key,
                        emoji_code = key,
                        countReactions = groupBy[key]!!.size
                    )
                )
                userId = false
            }
        }
        return reactionsItem
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    companion object {
        const val MOCK_COUNT = 1000
    }
}
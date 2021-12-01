package com.bogsnebes.tinkoffcurs.data.impl

import com.bogsnebes.tinkoffcurs.App.Companion.retrofit
import com.bogsnebes.tinkoffcurs.data.AppDatabase
import com.bogsnebes.tinkoffcurs.data.remote.Result
import com.bogsnebes.tinkoffcurs.data.remote.messages.ListMessages
import com.bogsnebes.tinkoffcurs.data.dto.Message
import com.bogsnebes.tinkoffcurs.data.remote.messages.MessagesApi
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MessagesImpl {
    private val db = AppDatabase.instance
    private val messagesApi = retrofit.create(MessagesApi::class.java)

    fun loadMessages(
        num_before: Int,
        num_after: Int,
        nameTopic: String
    ): Observable<List<Message>> {
        return getMessages(num_before, num_after, nameTopic).map { it.messages }
    }

    fun sendMessage(
        streamName: String,
        topic: String,
        content: String,
    ): Observable<Result> {
        return messagesApi.sendMessage(
            streamName = streamName,
            topic = topic,
            content = content
        ).subscribeOn(Schedulers.io())
    }

    private fun getMessages(
        num_before: Int,
        num_after: Int,
        nameTopic: String
    ): Observable<ListMessages> {
        return messagesApi.getMessages(
            "newest",
            num_before,
            num_after,
            "[{\"operand\": \"$nameTopic\", \"operator\": \"topic\"}]"
        )
    }
}
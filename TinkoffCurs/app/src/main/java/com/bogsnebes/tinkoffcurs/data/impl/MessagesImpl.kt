package com.bogsnebes.tinkoffcurs.data.impl

import com.bogsnebes.tinkoffcurs.App.Companion.retrofit
import com.bogsnebes.tinkoffcurs.data.remote.messages.ListMessages
import com.bogsnebes.tinkoffcurs.data.remote.messages.Message
import com.bogsnebes.tinkoffcurs.data.remote.messages.MessagesApi
import io.reactivex.Observable

class MessagesImpl {
    private val messagesApi = retrofit.create(MessagesApi::class.java)

    fun loadMessages(
        num_before: Int,
        num_after: Int,
        nameTopic: String
    ): Observable<List<Message>> {
        return getMessages(num_before, num_after, nameTopic).map { it.messages }
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
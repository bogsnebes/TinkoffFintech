package com.bogsnebes.tinkoffcurs.data.impl

import com.bogsnebes.tinkoffcurs.App.Companion.retrofit
import com.bogsnebes.tinkoffcurs.data.remote.streams.StreamsApi
import com.bogsnebes.tinkoffcurs.data.remote.streams.stream.ListStreams
import com.bogsnebes.tinkoffcurs.data.remote.streams.stream.Stream
import io.reactivex.Observable


class StreamsImpl {
    private val streamsApi = retrofit.create(StreamsApi::class.java)

    fun loadStreams(searchQuery: String, subscribe: Boolean): Observable<List<Stream>> {
        return getStreams(subscribe).map { streams ->
            streams.streams.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }
    }

    private fun getStreams(subscribe: Boolean): Observable<ListStreams> {
        return if (subscribe) {
            streamsApi.getSubscriptions()
        } else
            streamsApi.getAllStreams()
    }
}
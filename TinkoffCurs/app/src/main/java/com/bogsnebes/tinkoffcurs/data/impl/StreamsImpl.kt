package com.bogsnebes.tinkoffcurs.data.impl

import com.bogsnebes.tinkoffcurs.App.Companion.retrofit
import com.bogsnebes.tinkoffcurs.data.AppDatabase
import com.bogsnebes.tinkoffcurs.data.dto.Stream
import com.bogsnebes.tinkoffcurs.data.remote.streams.StreamsApi
import com.bogsnebes.tinkoffcurs.data.remote.streams.stream.ListStreams
import io.reactivex.Observable
import io.reactivex.Single


class StreamsImpl {
    private val db = AppDatabase.instance
    private val streamsApi = retrofit.create(StreamsApi::class.java)

    fun loadStreams(searchQuery: String, subscribe: Boolean): Single<List<Stream>>? {
        return Observable.concat(getStreams(subscribe).map { streams ->
            streams.streams
        }, getCacheStreams()).first(null)
    }

    private fun getStreams(subscribe: Boolean): Observable<ListStreams> {
        return if (subscribe) {
            streamsApi.getSubscriptions()
        } else
            streamsApi.getAllStreams()
    }

    private fun getCacheStreams(): Observable<List<Stream>> {
        return Observable.fromCallable { db.streamDao().getMessages() }
    }
}

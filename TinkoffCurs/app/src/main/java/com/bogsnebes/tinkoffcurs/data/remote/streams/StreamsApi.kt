package com.bogsnebes.tinkoffcurs.data.remote.streams

import com.bogsnebes.tinkoffcurs.data.remote.streams.stream.ListStreams
import com.bogsnebes.tinkoffcurs.data.remote.streams.topic.ListTopics
import io.reactivex.Observable
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StreamsApi {
    @GET("users/me/subscriptions")
    fun getSubscriptions(): Observable<ListStreams>

    @GET("users/me/{stream_id}/topics")
    fun getStreamTopics(@Path("stream_id") stream_id: Int): Observable<ListTopics>

    @GET("users/me/{stream_name}/topics")
    fun getStreamTopics(@Path("stream_name") stream_name: String): Observable<ListTopics>

    @GET("streams")
    fun getAllStreams(): Observable<ListStreams>

    @DELETE("users/me/subscriptions")
    fun removeSubscriptions()

    @POST("users/me/subscriptions")
    fun addSubscriptions()
}
package com.bogsnebes.tinkoffcurs.data.remote.streams

import com.bogsnebes.tinkoffcurs.App.Companion.API_KEY
import com.bogsnebes.tinkoffcurs.data.remote.streams.stream.ListStreams
import com.bogsnebes.tinkoffcurs.data.remote.streams.topic.ListTopics
import io.reactivex.Observable
import retrofit2.http.*

interface StreamsApi {
    @Headers("Authorization: $API_KEY")
    @GET("users/me/subscriptions")
    fun getSubscriptions(): Observable<ListStreams>

    @Headers("Authorization: $API_KEY")
    @GET("users/me/{stream_id}/topics")
    fun getStreamTopics(@Path("stream_id") stream_id: Int): Observable<ListTopics>

    @Headers("Authorization: $API_KEY")
    @GET("users/me/{stream_name}/topics")
    fun getStreamTopics(@Path("stream_name") stream_name: String): Observable<ListTopics>

    @Headers("Authorization: $API_KEY")
    @GET("streams")
    fun getAllStreams(): Observable<ListStreams>

    @Headers("Authorization: $API_KEY")
    @DELETE("users/me/subscriptions")
    fun removeSubscriptions() // Погугли

    @Headers("Authorization: $API_KEY")
    @POST("users/me/subscriptions")
    fun addSubscriptions() // Погугли
}
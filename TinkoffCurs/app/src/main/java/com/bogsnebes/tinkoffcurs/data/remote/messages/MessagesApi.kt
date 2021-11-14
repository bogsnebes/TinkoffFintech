package com.bogsnebes.tinkoffcurs.data.remote.messages

import io.reactivex.Observable
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MessagesApi {
    @POST("messages")
    fun sendMessage()

    @GET("messages")
    fun getMessages(
        @Query("anchor") anchor: String,
        @Query("num_before") num_before: Int,
        @Query("num_after") num_after: Int,
        @Query("narrow") narrow: String
    ): Observable<ListMessages>

    @GET("messages/{message_id}/reactions")
    fun addReaction()

    @DELETE("messages/{message_id}/reactions")
    fun removeReaction()
}
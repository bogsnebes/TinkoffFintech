package com.bogsnebes.tinkoffcurs.data.remote.messages

import com.bogsnebes.tinkoffcurs.App.Companion.API_KEY
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface MessagesApi {
    @Headers("Authorization: $API_KEY")
    @POST("messages")
    fun sendMessage()

    @Headers("Authorization: $API_KEY")
    @GET("messages")
    fun getMessages(
        @Query("anchor") anchor: Int,
        @Query("num_before") num_before: Int,
        @Query("num_after") num_after: Int,
        @Query("narrow") narrow: String
    ): Observable<Call<ListMessages>>

    @Headers("Authorization: $API_KEY")
    @GET("messages/{message_id}/reactions")
    fun addReaction()

    @Headers("Authorization: $API_KEY")
    @DELETE("messages/{message_id}/reactions")
    fun removeReaction()
}
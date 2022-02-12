package com.bogsnebes.tinkoffcurs.data.remote.messages

import com.bogsnebes.tinkoffcurs.data.remote.Result
import io.reactivex.Observable
import retrofit2.http.*

interface MessagesApi {
    @FormUrlEncoded
    @POST("messages")
    fun sendMessage(
        @Field("type") type: String = "stream",
        @Field("to") streamName: String,
        @Field("topic") topic: String,
        @Field("content") content: String,
    ): Observable<Result>

    @GET("messages")
    fun getMessages(
        @Query("anchor") anchor: String,
        @Query("num_before") num_before: Int,
        @Query("num_after") num_after: Int,
        @Query("narrow") narrow: String
    ): Observable<ListMessages>

    @FormUrlEncoded
    @POST("messages/{message_id}/reactions")
    fun addReaction(
        @Path("message_id") messageId: Long,
        @Field("emoji_code") emojiCode: String,
    ): Observable<Result>

    @DELETE("messages/{message_id}/reactions")
    fun removeReaction(
        @Path("message_id") messageId: Long,
        @Field("emoji_code") emojiCode: String,
    ): Observable<Result>
}
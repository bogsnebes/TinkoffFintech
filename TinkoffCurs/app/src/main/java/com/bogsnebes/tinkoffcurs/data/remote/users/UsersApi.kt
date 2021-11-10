package com.bogsnebes.tinkoffcurs.data.remote.users

import com.bogsnebes.tinkoffcurs.App.Companion.API_KEY
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UsersApi {
    @Headers("Authorization: $API_KEY")
    @GET("users")
    fun getMembers(): Observable<ListUsers>

    @Headers("Authorization: $API_KEY")
    @GET("users/{user_id}")
    fun getUserById(@Path("user_id") user_id: Int): Observable<User>
}
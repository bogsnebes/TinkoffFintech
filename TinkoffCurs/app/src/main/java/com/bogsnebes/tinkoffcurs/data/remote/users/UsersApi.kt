package com.bogsnebes.tinkoffcurs.data.remote.users

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersApi {
    @GET("users")
    fun getMembers(): Observable<ListUsers>

    @GET("users/{user_id}")
    fun getUserById(@Path("user_id") user_id: Int): Observable<User>
}
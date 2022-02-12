package com.bogsnebes.tinkoffcurs.data.impl

import androidx.annotation.WorkerThread
import com.bogsnebes.tinkoffcurs.App
import com.bogsnebes.tinkoffcurs.data.remote.users.ListUsers
import com.bogsnebes.tinkoffcurs.data.remote.users.User
import com.bogsnebes.tinkoffcurs.data.remote.users.UsersApi
import com.bogsnebes.tinkoffcurs.ui.chat.ChatFragment
import io.reactivex.Observable

class PeopleImpl {
    private val usersApi = App.retrofit.create(UsersApi::class.java)
    fun loadProfiles(searchQuery: String): Observable<List<User>> {
        return getProfiles().map { profile ->
            profile.users.filter { it.fullName.contains(searchQuery, ignoreCase = true) }
        }
    }

    fun getMyProfile(): Observable<User> {
        return usersApi.getUserById(ChatFragment.USER_ID).map { it.user }
    }

    private fun getProfiles(): Observable<ListUsers> {
        return usersApi.getMembers()
    }
}
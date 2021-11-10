package com.bogsnebes.tinkoffcurs.ui.people

import com.bogsnebes.tinkoffcurs.ui.people.recycler.ProfileDto


sealed class PeopleScreenState {
    class Result(val items: List<ProfileDto>) : PeopleScreenState()

    object Loading : PeopleScreenState()

    class Error(val error: Throwable) : PeopleScreenState()
}
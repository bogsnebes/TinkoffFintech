package com.bogsnebes.tinkoffcurs.ui.people

import com.bogsnebes.tinkoffcurs.data.dto.ProfileDto


sealed class PeopleScreenState {
    class Result(val items: List<ProfileDto>) : PeopleScreenState()

    object Loading : PeopleScreenState()

    class Error(val error: Throwable) : PeopleScreenState()
}
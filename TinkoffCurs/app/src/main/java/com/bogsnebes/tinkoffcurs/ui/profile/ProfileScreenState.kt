package com.bogsnebes.tinkoffcurs.ui.profile

import com.bogsnebes.tinkoffcurs.ui.people.recycler.ProfileDto
import timber.log.Timber


sealed class ProfileScreenState {
    class Result(val item: ProfileDto) : ProfileScreenState()

    object Loading : ProfileScreenState()

    class Error(val error: Throwable) : ProfileScreenState() {
        init {
            Timber.e(error)
        }
    }
}
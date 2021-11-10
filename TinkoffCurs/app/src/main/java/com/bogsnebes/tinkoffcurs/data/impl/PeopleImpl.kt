package com.bogsnebes.tinkoffcurs.data.impl

import androidx.annotation.WorkerThread
import com.bogsnebes.tinkoffcurs.data.TestData
import com.bogsnebes.tinkoffcurs.ui.people.recycler.ProfileDto
import io.reactivex.Observable

class PeopleImpl {

    fun loadProfiles(searchQuery: String): Observable<List<ProfileDto>> {
        return Observable.fromCallable { getProfiles() }
            .map { profile ->
                profile.filter { it.name.contains(searchQuery, ignoreCase = true) }
            }
    }

    @WorkerThread
    private fun getProfiles(): List<ProfileDto> {
        return TestData.testPeopleList
    }
}
package com.bogsnebes.tinkoffcurs.data.impl

import androidx.annotation.WorkerThread
import com.bogsnebes.tinkoffcurs.data.TestData
import com.bogsnebes.tinkoffcurs.data.dto.StreamDto
import io.reactivex.Observable


class StreamsImpl {
    fun loadStreams(searchQuery: String): Observable<List<StreamDto>> {
        return Observable.fromCallable { getStreams() }
            .map { streams ->
                streams.filter { it.category.contains(searchQuery, ignoreCase = true) }
            }
    }

    @WorkerThread
    private fun getStreams(): List<StreamDto> {
        return TestData.testStreams
    }
}
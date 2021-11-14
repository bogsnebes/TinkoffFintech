package com.bogsnebes.tinkoffcurs.ui.channels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bogsnebes.tinkoffcurs.App.Companion.retrofit
import com.bogsnebes.tinkoffcurs.data.impl.StreamsImpl
import com.bogsnebes.tinkoffcurs.data.remote.streams.StreamsApi
import com.bogsnebes.tinkoffcurs.ui.channels.viewPager.StreamsScreenState
import com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler.StreamItem
import com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler.TopicItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

internal class ChannelsViewModel : ViewModel() {
    private var _streamsScreenState: MutableLiveData<StreamsScreenState> = MutableLiveData()
    val streamsScreenState: LiveData<StreamsScreenState>
        get() = _streamsScreenState

    private val streamsImpl: StreamsImpl = StreamsImpl()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val searchSubject: PublishSubject<String> = PublishSubject.create()
    private val streamsApi = retrofit.create(StreamsApi::class.java)
    var subscribedFragment = false

    init {
        subscribeToSearchChanges()
        getAllStreams()
    }

    fun searchStream(searchQuery: String) {
        searchSubject.onNext(searchQuery)
    }

    private fun subscribeToSearchChanges() {
        searchSubject
            .subscribeOn(Schedulers.io())
            .distinctUntilChanged()
            .debounce(250, TimeUnit.MILLISECONDS, Schedulers.io())
            .switchMap { searchQuery -> streamsImpl.loadStreams(searchQuery, subscribedFragment) }
            .flatMapIterable { it }
            .concatMap { stream ->
                streamsApi.getStreamTopics(stream.streamId).map {
                    StreamItem(
                        name = stream.name,
                        topics = it.topics.map { topic ->
                            TopicItem(
                                category = stream.name,
                                name = topic.name,
                                countMessage = topic.maxMessageId,
                            )
                        }
                    )
                }
            }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _streamsScreenState.postValue(StreamsScreenState.Loading) }
            .subscribeBy(
                onSuccess = { _streamsScreenState.value = StreamsScreenState.Result(it) },
                onError = { _streamsScreenState.value = StreamsScreenState.Error(it) }
            )
            .addTo(compositeDisposable)
    }

    private fun getAllStreams() {
        streamsImpl.loadStreams("", subscribedFragment)
            .subscribeOn(Schedulers.io())
            .flatMapIterable { it }
            .concatMap { stream ->
                streamsApi.getStreamTopics(stream.streamId).map {
                    StreamItem(
                        name = stream.name,
                        topics = it.topics.map { topic ->
                            TopicItem(
                                category = stream.name,
                                name = topic.name,
                                countMessage = topic.maxMessageId,
                            )
                        }
                    )
                }
            }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _streamsScreenState.value = StreamsScreenState.Loading }
            .subscribeBy(
                onSuccess = { _streamsScreenState.value = StreamsScreenState.Result(it) },
                onError = { _streamsScreenState.value = StreamsScreenState.Error(it) }
            )
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
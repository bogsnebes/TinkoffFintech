package com.bogsnebes.tinkoffcurs.ui.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bogsnebes.tinkoffcurs.data.impl.PeopleImpl
import com.bogsnebes.tinkoffcurs.ui.channels.viewPager.StreamsScreenState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class PeopleViewModel : ViewModel() {
    private var _peopleScreenState: MutableLiveData<PeopleScreenState> = MutableLiveData()
    val peopleScreenState: LiveData<PeopleScreenState>
        get() = _peopleScreenState

    private val peopleImpl: PeopleImpl = PeopleImpl()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val searchSubject: PublishSubject<String> = PublishSubject.create()

    init {
        subscribeToSearchChanges()
        getAllStreams()
    }

    fun searchProfile(searchQuery: String) {
        searchSubject.onNext(searchQuery)
    }

    private fun subscribeToSearchChanges() {
        searchSubject
            .subscribeOn(Schedulers.io())
            .distinctUntilChanged()
            .debounce(250, TimeUnit.MILLISECONDS, Schedulers.io())
            .switchMap { searchQuery -> peopleImpl.loadProfiles(searchQuery) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _peopleScreenState.postValue(PeopleScreenState.Loading) }
            .subscribeBy(
                onNext = { _peopleScreenState.value = PeopleScreenState.Result(it) },
                onError = { _peopleScreenState.value = PeopleScreenState.Error(it) }
            )
            .addTo(compositeDisposable)
    }

    private fun getAllStreams() {
        peopleImpl.loadProfiles("")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _peopleScreenState.value = PeopleScreenState.Loading }
            .subscribeBy(
                onNext = { _peopleScreenState.value = PeopleScreenState.Result(it) },
                onError = { _peopleScreenState.value = PeopleScreenState.Error(it) }
            )
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
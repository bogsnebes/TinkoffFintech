package com.bogsnebes.tinkoffcurs.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bogsnebes.tinkoffcurs.data.impl.PeopleImpl
import com.bogsnebes.tinkoffcurs.ui.people.recycler.ProfileDto
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ProfileViewModel : ViewModel() {
    private var _profileScreenState: MutableLiveData<ProfileScreenState> = MutableLiveData()
    val profileScreenState: LiveData<ProfileScreenState>
        get() = _profileScreenState
    private val peopleImpl: PeopleImpl = PeopleImpl()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getMyProfile() {
        peopleImpl.getMyProfile()
            .subscribeOn(Schedulers.io())
            .map { user ->
                ProfileDto(
                    userId = user.userId,
                    avatar = user.avatarUrl,
                    name = user.fullName,
                    email = user.email,
                    online = true
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _profileScreenState.value = ProfileScreenState.Loading }
            .subscribeBy(
                onNext = {
                    _profileScreenState.value = ProfileScreenState.Result(it)
                },
                onError = {
                    _profileScreenState.value = ProfileScreenState.Error(it)
                })
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
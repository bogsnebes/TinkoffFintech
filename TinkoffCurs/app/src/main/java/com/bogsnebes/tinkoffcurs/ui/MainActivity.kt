package com.bogsnebes.tinkoffcurs.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.data.dto.ProfileDto
import com.bogsnebes.tinkoffcurs.ui.people.PeopleFragment
import com.bogsnebes.tinkoffcurs.ui.profile.ProfileFragment


class MainActivity : AppCompatActivity(R.layout.main_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PeopleFragment())
            .commitAllowingStateLoss()

        supportFragmentManager.setFragmentResultListener(
            PeopleFragment.PROFILE_OPEN_KEY, this
        ) { _, bundle ->
            (bundle.getSerializable(PeopleFragment.PROFILE_OPEN_KEY) as? ProfileDto)?.let {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, ProfileFragment.newInstance(it))
                    .hide(PeopleFragment())
                    .commit()
            }
        }
    }
}
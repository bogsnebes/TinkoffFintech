package com.bogsnebes.tinkoffcurs.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.data.dto.ProfileDto
import com.bogsnebes.tinkoffcurs.data.dto.TestData
import com.bogsnebes.tinkoffcurs.ui.channels.ChannelsFragment
import com.bogsnebes.tinkoffcurs.ui.people.PeopleFragment
import com.bogsnebes.tinkoffcurs.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(R.layout.main_activity),
    BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ChannelsFragment())
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
        val bottomNavigationMenu: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationMenu.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.channels -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ChannelsFragment())
                .commitAllowingStateLoss()
            R.id.people -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, PeopleFragment())
                .commitAllowingStateLoss()
            R.id.profile -> supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ProfileFragment.newInstance(TestData.testProfile))
                .commitAllowingStateLoss()
        }
        return true
    }
}
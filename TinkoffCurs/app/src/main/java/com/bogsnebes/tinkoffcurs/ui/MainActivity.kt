package com.bogsnebes.tinkoffcurs.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.ui.channels.ChannelsFragment
import com.bogsnebes.tinkoffcurs.ui.channels.viewPager.StreamsFragment
import com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler.TopicItem
import com.bogsnebes.tinkoffcurs.ui.chat.ChatFragment
import com.bogsnebes.tinkoffcurs.ui.people.PeopleFragment
import com.bogsnebes.tinkoffcurs.ui.people.recycler.ProfileDto
import com.bogsnebes.tinkoffcurs.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(R.layout.main_activity),
    BottomNavigationView.OnNavigationItemSelectedListener {
    private val channelsFragment by lazy { ChannelsFragment.newInstance() }
    private val peopleFragment by lazy { PeopleFragment.newInstance() }
    private val profileFragment by lazy { ProfileFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, channelsFragment)
            .add(R.id.container, peopleFragment)
            .add(R.id.container, profileFragment)
            .hide(peopleFragment)
            .hide(profileFragment)
            .commitNow()

        supportFragmentManager.setFragmentResultListener(
            PeopleFragment.PROFILE_OPEN_KEY, this
        ) { _, bundle ->
            (bundle.getSerializable(PeopleFragment.PROFILE_OPEN_KEY) as? ProfileDto)?.let {
                supportFragmentManager.beginTransaction()
                    .addToBackStack(PeopleFragment.TAG)
                    .add(R.id.container, ProfileFragment.newInstance(it))
                    .hide(peopleFragment)
                    .commit()
            }
        }
        val bottomNavigationMenu: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationMenu.setOnNavigationItemSelectedListener(this)

        supportFragmentManager.setFragmentResultListener(
            StreamsFragment.CHAT_OPEN_KEY, this
        ) { _, bundle ->
            (bundle.getSerializable(StreamsFragment.CHAT_OPEN_KEY) as? TopicItem)?.let {
                supportFragmentManager.beginTransaction()
                    .addToBackStack(ChannelsFragment.TAG)
                    .add(R.id.container, ChatFragment.newInstance(it))
                    .hide(channelsFragment)
                    .commit()
            }
        }

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                bottomNavigationMenu.visibility = View.GONE
            } else {
                bottomNavigationMenu.visibility = View.VISIBLE
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.channels -> supportFragmentManager
                .beginTransaction()
                .show(channelsFragment)
                .hide(peopleFragment)
                .hide(profileFragment)
                .commit()
            R.id.people -> supportFragmentManager
                .beginTransaction()
                .show(peopleFragment)
                .hide(channelsFragment)
                .hide(profileFragment)
                .commit()
            R.id.profile -> supportFragmentManager
                .beginTransaction()
                .show(profileFragment)
                .hide(peopleFragment)
                .hide(channelsFragment)
                .commit()
        }
        return true
    }
}
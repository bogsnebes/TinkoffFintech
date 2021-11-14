package com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler

import java.io.Serializable

data class TopicItem(
    var category: String,
    var name: String,
    val countMessage: Int
): Serializable
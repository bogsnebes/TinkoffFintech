package com.bogsnebes.tinkoffcurs.ui.people.recycler

import java.io.Serializable

data class ProfileDto(
    val userId: Int,
    val avatar: String?,
    val name: String,
    val email: String,
    val online: Boolean
): Serializable
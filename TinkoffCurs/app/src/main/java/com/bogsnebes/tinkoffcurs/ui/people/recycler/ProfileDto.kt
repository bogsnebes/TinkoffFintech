package com.bogsnebes.tinkoffcurs.ui.people.recycler

import java.io.Serializable

data class ProfileDto(
    val userId: Long,
    val avatar: String?,
    val name: String,
    val email: String,
    val online: Boolean
): Serializable
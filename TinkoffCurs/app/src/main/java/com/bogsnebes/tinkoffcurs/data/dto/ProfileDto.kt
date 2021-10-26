package com.bogsnebes.tinkoffcurs.data.dto

import java.io.Serializable

data class ProfileDto(
    val userId: Int,
    val avatar: String?,
    val name: String,
    val email: String,
    val online: Boolean
): Serializable
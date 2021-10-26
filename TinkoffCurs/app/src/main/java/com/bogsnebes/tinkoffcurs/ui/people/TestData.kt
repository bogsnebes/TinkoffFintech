package com.bogsnebes.tinkoffcurs.ui.people

import com.bogsnebes.tinkoffcurs.data.dto.ProfileDto

object TestData {
    val testList: MutableList<ProfileDto> = mutableListOf(
        ProfileDto(
            "https://memepedia.ru/wp-content/uploads/2018/08/papich-768x432.jpg",
            "Пользователь 1",
            "example@gmail.com",
            false
        ),
        ProfileDto(
            "https://starpri.ru/wp-content/uploads/2019/02/strimer-Papich.jpg",
            "Пользователь 2",
            "example@gmail.com",
            true
        ),
        ProfileDto(
            "https://c.tenor.com/O25ywbFIrawAAAAd/papich-arthas.gif",
            "Пользователь 3",
            "example@gmail.com",
            true
        ),
        ProfileDto(
            null,
            "Пользователь 4",
            "example@gmail.com",
            false
        )
    )
}
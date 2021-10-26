package com.bogsnebes.tinkoffcurs.data.dto

object TestData {
    val testPeopleList: MutableList<ProfileDto> = mutableListOf(
        ProfileDto(
            1,
            "https://memepedia.ru/wp-content/uploads/2018/08/papich-768x432.jpg",
            "Пользователь 1",
            "example@gmail.com",
            false
        ),
        ProfileDto(
            2,
            "https://starpri.ru/wp-content/uploads/2019/02/strimer-Papich.jpg",
            "Пользователь 2",
            "example@gmail.com",
            true
        ),
        ProfileDto(
            3,
            "https://c.tenor.com/O25ywbFIrawAAAAd/papich-arthas.gif",
            "Пользователь 3",
            "example@gmail.com",
            true
        ),
        ProfileDto(
            4,
            null,
            "Пользователь 4",
            "example@gmail.com",
            false
        )
    )
    val testProfile: ProfileDto = ProfileDto(
        0,
        "https://memepedia.ru/wp-content/uploads/2018/08/papich-768x432.jpg",
        "Пользователь 0",
        "example@gmail.com",
        true
    )
}
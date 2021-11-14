package com.bogsnebes.tinkoffcurs.data

import com.bogsnebes.tinkoffcurs.ui.people.recycler.ProfileDto

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

//    val testStreams = mutableListOf(
//        StreamItem(
//            "#general",
//            listOf(
//                ChatItem(
//                    "Testing",
//                    1240,
//                    mutableListOf(
//                        MessageDto(
//                            0, 123, "Писатель 1", "kappa", null, listOf(
//                                ReactionDto(123, "\uD83D\uDE00", 1),
//                                ReactionDto(123, "\uD83D\uDE00", 123451)
//                            ), "03.01.2020"
//                        ),
//                        MessageDto(
//                            1, 432,
//                            "Писатель 2",
//                            "чупапа муняня",
//                            null,
//                            listOf(), "03.01.2020"
//                        )
//                    )
//                ),
//                ChatItem(
//                    "Testing",
//                    1240,
//                    mutableListOf()
//                ),
//                ChatItem(
//                    "Testing",
//                    1240,
//                    mutableListOf()
//                )
//            )
//        ),
//        StreamItem(
//            "#directory",
//            listOf(
//                ChatItem(
//                    "Testing",
//                    1240,
//                    mutableListOf()
//                )
//            )
//        ),
//        StreamItem(
//            "#hr",
//            listOf(
//                ChatItem(
//                    "Testing",
//                    1240,
//                    mutableListOf()
//                )
//            )
//        ),
//    )
}
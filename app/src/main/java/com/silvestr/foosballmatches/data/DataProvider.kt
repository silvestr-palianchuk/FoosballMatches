package com.silvestr.foosballmatches.data

import java.util.concurrent.TimeUnit


object DataProvider {

    val gameIdsSet = mutableSetOf<Int>().apply {
        for (id in 7..1000) {
            add(id)
        }
    }

    val playerIdsSet = mutableSetOf<Int>().apply {
        for (id in 11..1000) {
            add(id)
        }
    }

    val players = mutableSetOf(
        Player(1, "Bruno", "Goncalves"),
        Player(2, "Yannick", "Correia"),
        Player(3, "Billy", "Pappas"),
        Player(4, "Tony", "Spredeman"),
        Player(5, "Todd", "Loffredo"),
        Player(6, "Frederic", "Collignon"),
        Player(7, "Brandon", "Brandon"),
        Player(8, "Tom", "Yore"),
        Player(9, "Dave ", "Gummeson"),
        Player(10, "Tracy", "McMillin")
    )

    private val currentDate = System.currentTimeMillis()

    val games = mutableListOf(
        Game(
            1,
            currentDate,
            players.find { it.id == 1 },
            players.find { it.id == 2 },
            5,
            6
        ),
        Game(
            2,
            currentDate,
            players.find { it.id == 3 },
            players.find { it.id == 4 },
            7,
            8
        ),
        Game(
            3,
            currentDate,
            players.find { it.id == 5 },
            players.find { it.id == 6 },
            10,
            5
        ),
        Game(
            4,
            currentDate,
            players.find { it.id == 7 },
            players.find { it.id == 8 },
            3,
            8
        ),
        Game(
            5,
            currentDate,
            players.find { it.id == 9 },
            players.find { it.id == 10 },
            9,
            5
        ),
        Game(
            6,
            currentDate,
            players.find { it.id == 9 },
            players.find { it.id == 8 },
            8,
            3
        )
    )
}
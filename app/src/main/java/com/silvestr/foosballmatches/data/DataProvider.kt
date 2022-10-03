package com.silvestr.foosballmatches.data


object DataProvider {

    val gameIdsSet = mutableSetOf<Int>().apply {
        for (id in 16..1000) {
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

    val games = mutableListOf(
        Game(
            1,
            System.currentTimeMillis(),
            players.find { it.id == 1 },
            players.find { it.id == 2 },
            5,
            6
        ),
        Game(
            2,
            System.currentTimeMillis(),
            players.find { it.id == 3 },
            players.find { it.id == 4 },
            7,
            8
        ),
        Game(
            3,
            System.currentTimeMillis(),
            players.find { it.id == 5 },
            players.find { it.id == 6 },
            10,
            5
        ),
        Game(
            4,
            System.currentTimeMillis(),
            players.find { it.id == 7 },
            players.find { it.id == 8 },
            3,
            8
        ),
        Game(
            5,
            System.currentTimeMillis(),
            players.find { it.id == 9 },
            players.find { it.id == 10 },
            5,
            9
        ),
        Game(
            6,
            System.currentTimeMillis(),
            players.find { it.id == 9 },
            players.find { it.id == 10 },
            3,
            10
        ),
        Game(
            7,
            System.currentTimeMillis(),
            players.find { it.id == 9 },
            players.find { it.id == 10 },
            1,
            11
        ),
        Game(
            8,
            System.currentTimeMillis(),
            players.find { it.id == 1 },
            players.find { it.id == 9 },
            1,
            9
        ),
        Game(
            9,
            System.currentTimeMillis(),
            players.find { it.id == 2 },
            players.find { it.id == 8 },
            2,
            8
        ),
        Game(
            10,
            System.currentTimeMillis(),
            players.find { it.id == 3 },
            players.find { it.id == 7 },
            7,
            3
        ),
        Game(
            11,
            System.currentTimeMillis(),
            players.find { it.id == 4 },
            players.find { it.id == 6 },
            4,
            6
        ),
        Game(
            12,
            System.currentTimeMillis(),
            players.find { it.id == 5 },
            players.find { it.id == 4 },
            5,
            4
        ),
        Game(
            13,
            System.currentTimeMillis(),
            players.find { it.id == 6 },
            players.find { it.id == 3 },
            6,
            3
        ),
        Game(
            14,
            System.currentTimeMillis(),
            players.find { it.id == 7 },
            players.find { it.id == 2 },
            7,
            2
        ),
        Game(
            15,
            System.currentTimeMillis(),
            players.find { it.id == 8 },
            players.find { it.id == 1 },
            8,
            1
        )
    )
}
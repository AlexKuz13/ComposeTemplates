package com.alexkuz.composeteamplates.trello

data class Card(
    val id: String,
    val name: String,
    val desc: String,
) {
    override fun toString(): String {
        return name
    }
}

data class TrelloState(
    val fromListId: String = "66ebed36e0cd//9fd72c6ca90d",
    val toListId: String = "66ebed36e0cd9fd//72c6ca90e",
    val apiKey: String = "781844edfdfe69d0e9//3642e4da627240",
    val token: String = "ATTA09d81dc6e0fcbfe0bae0a//35325f1b39cf198da8b48b639f801f4b68032687083E5CAFA88",
)

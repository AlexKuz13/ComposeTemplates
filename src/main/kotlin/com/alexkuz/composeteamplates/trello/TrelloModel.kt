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
    var fromListId: String = "",
    var toListId: String = "",
    var apiKey: String = "",
    var token: String = "",
)

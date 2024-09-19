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

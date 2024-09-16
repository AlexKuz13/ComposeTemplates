package com.alexkuz.composeteamplates.trello

interface TrelloFormView {
    fun showCards(cards: List<Card>)
    fun success()
    fun error(error: Throwable)
}
class Card
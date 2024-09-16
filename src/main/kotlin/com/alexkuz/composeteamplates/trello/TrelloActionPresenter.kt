package com.alexkuz.composeteamplates.trello

interface TrelloActionPresenter {
    fun loadCards()
    fun moveCard(card: Card)
}

class TrelloActionPresenterImpl(
    val view: TrelloFormView,
    val repository: TrelloRepository,
): TrelloActionPresenter {
    override fun loadCards() {
        TODO("Not yet implemented")
    }

    override fun moveCard(card: Card) {
        TODO("Not yet implemented")
    }

}
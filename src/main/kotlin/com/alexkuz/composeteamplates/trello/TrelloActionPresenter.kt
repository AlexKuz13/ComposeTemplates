package com.alexkuz.composeteamplates.trello

interface TrelloActionPresenter {
    fun loadCards()
    fun moveCard(card: Card)
}

class TrelloActionPresenterImpl(
    val view: TrelloFormView,
    val repository: TrelloRepository,
): TrelloActionPresenter {

    val fromListId = ""
    val toListId = ""
    val apiKey = ""
    val token = ""

    override fun loadCards() {
        repository.getCards(fromListId, apiKey, token)
            .subscribe(
                { cards -> view.showCards(cards) },
                { error -> view.error(error) }
            )
    }

    override fun moveCard(card: Card) {
        repository.moveCard(card.id, toListId, apiKey, token)
            .subscribe(
                { view.success() },
                { error -> view.error(error) }
            )
    }

}
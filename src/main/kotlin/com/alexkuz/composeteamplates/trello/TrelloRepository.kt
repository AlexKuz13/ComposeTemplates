package com.alexkuz.composeteamplates.trello

interface TrelloRepository {
    fun getCards(): List<Card>
    fun moveCard()
}

class TrelloRepositoryImpl : TrelloRepository {
    override fun getCards(): List<Card> {
        TODO("Not yet implemented")
    }

    override fun moveCard() {
        TODO("Not yet implemented")
    }
}
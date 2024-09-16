package com.alexkuz.composeteamplates.trello

interface TrelloInjector {
    val repository: TrelloRepository
    fun trelloActionPresenter(view: TrelloFormView): TrelloActionPresenter
}

class TrelloInjectorImpl: TrelloInjector {
    override val repository: TrelloRepository by lazy(LazyThreadSafetyMode.NONE) {
        TrelloRepositoryImpl()
    }

    override fun trelloActionPresenter(view: TrelloFormView): TrelloActionPresenter {
        return TrelloActionPresenterImpl(view, repository)
    }
}
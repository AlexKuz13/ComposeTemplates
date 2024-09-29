package com.alexkuz.composeteamplates.trello

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class TrelloActionPresenterImplTest {

    private lateinit var testee: TrelloActionPresenterImpl
    private var mockView: TrelloFormView = mock()
    private var mockRepository: TrelloRepository = mock()

    @Before
    fun setUp() {
        val state = TrelloState("apiKey", "token", "fromListId", "toListId")

        testee = TrelloActionPresenterImpl(mockView, mockRepository, state)
    }

    @Test
    fun whenLoadCardsExecutedThenShowCardsIsCalled() {
        val cards = listOf(Card("id", "name", "desc"))
        whenever(mockRepository.getCards(any(), any(), any())).thenReturn(Single.just(cards))

        testee.loadCards()

        verify(mockView).showCards(cards)
    }

    @Test
    fun whenLoadCardsExecutedWIthErrorThenErrorIsCalled() {
        val exception = Exception()
        whenever(mockRepository.getCards(any(), any(), any())).thenReturn(Single.error(exception))

        testee.loadCards()

        verify(mockView).error(exception)
    }
}
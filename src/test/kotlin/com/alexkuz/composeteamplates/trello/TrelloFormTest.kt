package com.alexkuz.composeteamplates.trello

import com.intellij.openapi.project.Project
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.assertj.swing.core.matcher.FrameMatcher.withName
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager
import org.assertj.swing.edt.GuiActionRunner
import org.assertj.swing.edt.GuiQuery
import org.assertj.swing.fixture.Containers.showInFrame
import org.assertj.swing.fixture.FrameFixture
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.timeout
import org.junit.Assert.assertEquals

class TrelloFormTest {
    private lateinit var frame: FrameFixture
    private val mockServiceApi: TrelloServiceApi = mock()
    private var mockRepository: TrelloRepository = mock()

    @Before
    fun before() {
        whenever(mockRepository.getCards("fromListId", "apiKey", "token"))
            .thenReturn(
                Single.just(listOf(Card("id", "My name", "My description")))
            )

        val injector = TrelloTestInjector(mockServiceApi, mockRepository)
        val form: TrelloForm? = GuiActionRunner.execute(object: GuiQuery<TrelloForm?>() {
            override fun executeInEDT(): TrelloForm? {
                val project: Project = mock()
                return TrelloForm(project = project, trelloInjector = injector)
            }
        })
        frame = showInFrame(form?.rootPane)
    }

    @Test
    fun whenDialogShownThenFirstCardSelectedShowsTheCorrectDescription() {
        timeout(100L)
        val textBox = frame.textBox("descriptionPane")

        assertEquals("My description", textBox.text())
    }

    @After
    fun tearDown() {
        frame.cleanUp()
    }

    companion object {
        @JvmStatic
        @BeforeClass
        fun setUpOnce() {
            FailOnThreadViolationRepaintManager.install()
        }
    }
}

class TrelloTestInjector(private val mockServiceApi: TrelloServiceApi, private val mockRepository: TrelloRepository): TrelloInjector {

    override val repository: TrelloRepository
        get() = mockRepository
    override val trelloServiceApi: TrelloServiceApi
        get() = mockServiceApi

    override fun trelloState(project: Project): TrelloState {
        return TrelloState("apiKey", "token", "fromListId", "toListId")
    }

    override fun trelloActionPresenter(view: TrelloFormView, project: Project): TrelloActionPresenter {
        return TrelloActionPresenterImpl(view, repository, trelloState(project))
    }

}
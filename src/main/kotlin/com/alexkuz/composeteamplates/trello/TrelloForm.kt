package com.alexkuz.composeteamplates.trello

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.COLUMNS_LARGE
import com.intellij.ui.dsl.builder.columns
import com.intellij.ui.dsl.builder.panel
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JTextPane

class TrelloForm(
    val project: Project,
    trelloInjector: TrelloInjector,
) : DialogWrapper(project), TrelloFormView {

    private val nameCombo: ComboBox<Card> = ComboBox<Card>().apply {
        name = "nameCombo"
    }
    private val descriptionPane: JTextPane = JTextPane().apply {
        name = "descriptionPane"
        isEditable = false
    }

    init {
        init()
    }

    override fun createCenterPanel(): JComponent = panel {
         row("Name: ") {
             cell(nameCombo).columns(COLUMNS_LARGE)
         }
        row("Description: ") {
            cell(descriptionPane)
        }
    }.apply {
        minimumSize = Dimension(500,340)
        preferredSize = Dimension(500, 340)
    }

    override fun showCards(cards: List<Card>) {
        TODO("Not yet implemented")
    }

    override fun success() {
        TODO("Not yet implemented")
    }

    override fun error(error: Throwable) {
        TODO("Not yet implemented")
    }


}
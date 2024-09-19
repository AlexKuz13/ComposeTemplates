package com.alexkuz.composeteamplates.trello

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.ApplicationManager
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
    private val project: Project,
    trelloInjector: TrelloInjector,
) : DialogWrapper(project), TrelloFormView {

    private val nameCombo: ComboBox<Card> = ComboBox<Card>().apply {
        name = "nameCombo"
    }
    private val descriptionPane: JTextPane = JTextPane().apply {
        name = "descriptionPane"
        isEditable = false
    }

    private val presenter: TrelloActionPresenter by lazy {
        trelloInjector.trelloActionPresenter(this, project)
    }

    init {
        init()

        nameCombo.addActionListener {
            val card = nameCombo.selectedItem as Card?

            card?.let {
                descriptionPane.text = it.desc
            }
        }

        presenter.loadCards()
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
        cards.map {
            nameCombo.addItem(it)
        }

        if (cards.isNotEmpty()) {
            nameCombo.selectedIndex = 0
        }
    }

    override fun success() {
        close(OK_EXIT_CODE)

        ApplicationManager.getApplication().invokeLater {
            val notificationGroup = NotificationGroup("success", NotificationDisplayType.BALLOON, true)
            notificationGroup.createNotification(
                "Success",
                "Card move to the new list",
                NotificationType.INFORMATION,
                null
            ).notify(project)
        }
    }

    override fun error(error: Throwable) {
        ApplicationManager.getApplication().invokeLater {
            val notificationGroup = NotificationGroup("error", NotificationDisplayType.BALLOON, true)
            notificationGroup.createNotification(
                "Error",
                error.localizedMessage,
                NotificationType.ERROR,
                null
            ).notify(project)
        }
    }
}
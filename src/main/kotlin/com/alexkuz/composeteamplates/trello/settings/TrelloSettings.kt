package com.alexkuz.composeteamplates.trello.settings

import com.alexkuz.composeteamplates.trello.TrelloState
import com.alexkuz.composeteamplates.trello.services.TrelloService
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.ui.dsl.builder.COLUMNS_LARGE
import com.intellij.ui.dsl.builder.columns
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JPasswordField
import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class TrelloSettings(private val project: Project) : Configurable, DocumentListener {

    private val state: TrelloState by lazy { TrelloService.getInstance(project).state }

    private var modified = false

    private val apiKeyField: JPasswordField = JPasswordField()
    private val tokenField: JPasswordField = JPasswordField()
    private val fromListIdField: JTextField = JTextField()
    private val toListIdField: JTextField = JTextField()
    private val panel: JPanel = panel {
        row("API key") { cell(apiKeyField).columns(COLUMNS_LARGE) }
        row("Token") { cell(tokenField).columns(COLUMNS_LARGE) }
        row("From list id") { cell(fromListIdField).columns(COLUMNS_LARGE) }
        row("To list id") { cell(toListIdField).columns(COLUMNS_LARGE) }
    }


    override fun createComponent(): JComponent {
        apiKeyField.apply {
            text = state.apiKey
            document.addDocumentListener(this@TrelloSettings)
        }
        tokenField.apply {
            text = state.token
            document.addDocumentListener(this@TrelloSettings)
        }
        fromListIdField.apply {
            text = state.fromListId
            document.addDocumentListener(this@TrelloSettings)
        }
        toListIdField.apply {
            text = state.toListId
            document.addDocumentListener(this@TrelloSettings)
        }
        return panel
    }

    override fun isModified(): Boolean = modified

    override fun apply() {
        state.apiKey = String(apiKeyField.password)
        state.token = String(tokenField.password)
        state.fromListId = fromListIdField.text
        state.toListId = toListIdField.text

        TrelloService.getInstance(project).loadState(state)
        modified = false
    }

    override fun getDisplayName() = "Trello Settings"


    override fun insertUpdate(e: DocumentEvent?) {
        modified = true
    }

    override fun removeUpdate(e: DocumentEvent?) {
        modified = true
    }

    override fun changedUpdate(e: DocumentEvent?) {
        modified = true
    }
}
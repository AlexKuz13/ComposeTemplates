package com.alexkuz.composeteamplates.trello

import com.alexkuz.composeteamplates.trello.services.TrelloService
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class TrelloAction : AnAction() {

    override fun actionPerformed(p0: AnActionEvent) {
        val dialog = TrelloForm(p0.project!!, TrelloInjectorImpl())
        dialog.show()
    }

    override fun update(e: AnActionEvent) {
        val state = TrelloService.getInstance(e.project!!).state
        e.presentation.isEnabled = state.apiKey.isNotBlank() && state.token.isNotBlank() &&
                state.fromListId.isNotBlank() && state.toListId.isNotBlank()
    }
}
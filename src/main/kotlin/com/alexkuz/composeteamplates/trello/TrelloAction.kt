package com.alexkuz.composeteamplates.trello

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class TrelloAction: AnAction() {

    override fun actionPerformed(p0: AnActionEvent) {
        val dialog = TrelloForm(p0.project!!, TrelloInjectorImpl())
        dialog.show()
    }
}
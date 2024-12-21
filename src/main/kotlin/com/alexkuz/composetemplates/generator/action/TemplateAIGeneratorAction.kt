package com.alexkuz.composetemplates.generator.action

import com.alexkuz.composetemplates.generator.service.AuthService
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class TemplateAIGeneratorAction: AnAction() {

    override fun actionPerformed(p0: AnActionEvent) {
        // TODO:  
    }

    override fun update(e: AnActionEvent) {
        val state = AuthService.getInstance(e.project!!).state
        e.presentation.isEnabled = state.token.isNotBlank() && state.catalogId.isNotBlank()
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }
}
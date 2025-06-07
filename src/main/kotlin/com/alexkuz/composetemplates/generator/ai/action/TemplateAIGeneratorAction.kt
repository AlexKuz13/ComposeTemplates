package com.alexkuz.composetemplates.generator.ai.action

import com.alexkuz.composetemplates.generator.ai.injector.TemplateGeneratorInjectorImpl
import com.alexkuz.composetemplates.generator.ai.model.AuthState
import com.alexkuz.composetemplates.generator.ai.service.AuthService
import com.alexkuz.composetemplates.generator.ai.view.TemplateAIGeneratorForm
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class TemplateAIGeneratorAction: AnAction() {

    override fun actionPerformed(p0: AnActionEvent) {
        val dialog = TemplateAIGeneratorForm(p0.project!!, TemplateGeneratorInjectorImpl())
        dialog.show()
    }

    override fun update(e: AnActionEvent) {
        val state = AuthService.getInstance(e.project!!)?.state ?: AuthState()
        e.presentation.isEnabled = state.token.isNotBlank() && state.catalogId.isNotBlank()
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }
}
package com.alexkuz.composetemplates.generator.action

import com.alexkuz.composetemplates.generator.view.TemplateGeneratorForm
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class TemplateGeneratorAction: AnAction() {

    override fun actionPerformed(p0: AnActionEvent) {
        val dialog = TemplateGeneratorForm(p0.project!!)
        dialog.show()
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }
}
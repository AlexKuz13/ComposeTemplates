package com.alexkuz.composetemplates

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class DemoAction(): AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        Messages.showMessageDialog(
            e.project,
            "Great! You just created your first action!",
            "My First Action",
            Messages.getInformationIcon())
    }
}
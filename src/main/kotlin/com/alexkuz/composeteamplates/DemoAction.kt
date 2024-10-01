package com.alexkuz.composeteamplates

import com.alexkuz.composeteamplates.trello.utils.StringsBundle

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import java.util.Calendar

class DemoAction(): AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        Messages.showMessageDialog(
            e.project,
            StringsBundle.string("hello", Calendar.getInstance().time),
            "My First Action",
            Messages.getInformationIcon())
    }
}
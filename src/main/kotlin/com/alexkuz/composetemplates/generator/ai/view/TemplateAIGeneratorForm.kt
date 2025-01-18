package com.alexkuz.composetemplates.generator.ai.view

import com.alexkuz.composetemplates.generator.ai.injector.TemplateGeneratorInjector
import com.alexkuz.composetemplates.generator.utils.DirectoryHelper
import com.alexkuz.composetemplates.generator.utils.getAppPackage
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.dsl.builder.*
import com.intellij.util.ui.JBUI
import java.awt.Dimension
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import javax.swing.*

class TemplateAIGeneratorForm(
    private val project: Project,
    injector: TemplateGeneratorInjector,
) : DialogWrapper(project) {

    private val composableNameField: JTextField = JTextField().apply { text = "MainScreen" }
    private val packageNameField: JTextField = JTextField().apply { text = project.getAppPackage() }
    private val requestField: JTextArea = JTextArea(10, 30).apply {
        lineWrap = true
        wrapStyleWord = true
        text = HINT
        foreground = JBColor.GRAY
    }
    private val scrollPane = JBScrollPane(requestField).apply {
        border = JBUI.Borders.empty(10)
    }
    private val progressBar = JProgressBar().apply {
        isVisible = false
        isIndeterminate = true
    }
    private val repository = injector.repository(project)

    init {
        init()

        requestField.addFocusListener(object : FocusAdapter() {
            override fun focusGained(e: FocusEvent) {
                if (requestField.text == HINT) {
                    requestField.text = ""
                    requestField.foreground = JBColor.BLACK
                }
            }

            override fun focusLost(e: FocusEvent) {
                if (requestField.text.isEmpty()) {
                    requestField.text = HINT
                    requestField.foreground = JBColor.GRAY
                }
            }
        })
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row(" Composable name") { cell(composableNameField).columns(COLUMNS_LARGE) }
            row(" Package name") { cell(packageNameField).columns(COLUMNS_LARGE) }
            separator()
            row { cell(scrollPane).align(Align.FILL) }
            row { cell(progressBar).align(Align.FILL) }
        }.apply {
            minimumSize = Dimension(600, 310)
            preferredSize = Dimension(600, 310)
        }
    }

    override fun doOKAction() {
        val packageName = packageNameField.text
        val composableName = composableNameField.text
        progressBar.isVisible = true
        val requestText = requestField.text + ". Его название $composableName"
        repository.getTemplate(requestText)
            .subscribe(
                { content ->
                    val contentWithPackage = "package $packageName\n\n$content"
                    val error = DirectoryHelper.generateScreenOrError(project, packageName, composableName, contentWithPackage)
                    if (error != null) {
                        progressBar.isVisible = false
                        Messages.showMessageDialog(project, error, "Error", Messages.getErrorIcon())
                    } else {
                        super.doOKAction()
                    }
                },
                { errorApi ->
                    progressBar.isVisible = false
                    Messages.showMessageDialog(project, errorApi.message, "Error", Messages.getErrorIcon())
                })
    }

    private companion object {
        private const val HINT = "Введите свой запрос сюда. Пример: Экран с двумя кнопками, расположенными по вертикали"
    }
}
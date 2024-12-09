package com.alexkuz.composetemplates.generator.settings

import com.alexkuz.composetemplates.generator.model.AuthState
import com.alexkuz.composetemplates.generator.service.AuthService
import com.alexkuz.composetemplates.utils.lazyUnsafe
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.ui.JBColor
import com.intellij.ui.dsl.builder.COLUMNS_LARGE
import com.intellij.ui.dsl.builder.columns
import com.intellij.ui.dsl.builder.panel
import com.jetbrains.rd.util.URI
import org.jetbrains.skiko.Cursor
import java.awt.Desktop
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class TemplateGeneratorSettings(private val project: Project) : Configurable, DocumentListener {

    private val state: AuthState by lazyUnsafe { AuthService.getInstance(project).state }

    private var isModified: Boolean = false

    private val tokenField: JPasswordField = JPasswordField()
    private val catalogField: JTextField = JTextField()
    private val instructionField: JLabel = JLabel("How to get credentials").apply {
        foreground = JBColor.BLUE
        cursor = Cursor(Cursor.HAND_CURSOR)
    }

    private val panel: JPanel = panel {
        group(title = "Credentials") {
            row("Token") { cell(tokenField).columns(COLUMNS_LARGE) }
            row("Catalog Id") { cell(catalogField).columns(COLUMNS_LARGE) }
            row { cell(instructionField) }
        }
    }

    override fun createComponent(): JComponent {
        tokenField.apply {
            text = state.token
            document.addDocumentListener(this@TemplateGeneratorSettings)
        }
        catalogField.apply {
            text = state.catalogId
            document.addDocumentListener(this@TemplateGeneratorSettings)
        }
        instructionField.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                Desktop.getDesktop()
                    .browse(URI("https://yandex.cloud/ru/docs/foundation-models/api-ref/authentication"))
            }
        })

        return panel
    }

    override fun isModified() = isModified

    override fun apply() {
        state.token = String(tokenField.password)
        state.catalogId = catalogField.text

        AuthService.getInstance(project).loadState(state)
        isModified = false
    }

    override fun getDisplayName() = "Template Compose AI Generator"

    override fun insertUpdate(e: DocumentEvent?) {
        isModified = true
    }

    override fun removeUpdate(e: DocumentEvent?) {
        isModified = true
    }

    override fun changedUpdate(e: DocumentEvent?) {
        isModified = true
    }
}
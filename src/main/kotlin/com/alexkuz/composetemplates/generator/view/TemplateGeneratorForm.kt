package com.alexkuz.composetemplates.generator.view

import com.alexkuz.composetemplates.generator.model.ComposeElements
import com.alexkuz.composetemplates.generator.model.TemplateGeneratorModel
import com.alexkuz.composetemplates.generator.utils.DirectoryHelper
import com.alexkuz.composetemplates.generator.utils.generateContent
import com.alexkuz.composetemplates.generator.utils.getAppPackage
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.dsl.builder.COLUMNS_LARGE
import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.columns
import com.intellij.ui.dsl.builder.panel
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextField

class TemplateGeneratorForm(
    private val project: Project,
) : DialogWrapper(project) {

    private val generatorModel = TemplateGeneratorModel()

    private val rowCountCombo: ComboBox<Int> = ComboBox(arrayOf(1, 2, 3))
    private val composableNameField: JTextField = JTextField().apply { text = "MainScreen" }
    private val packageNameField: JTextField = JTextField().apply { text = project.getAppPackage() }

    private val panel = JPanel()

    init {
        init()

        rowCountCombo.addActionListener {
            initGeneratorModel()
            updateCenterPanel()
        }

        rowCountCombo.selectedIndex = 0 // имитируем выбор первого элемента, чтобы показался след шаг
    }

    override fun createCenterPanel(): JComponent {
        val panel = panel {
            row(" Composable name") { cell(composableNameField).columns(COLUMNS_LARGE) }
            row(" Package name") { cell(packageNameField).columns(COLUMNS_LARGE) }
            row(" Количество секций по вертикали:") { cell(rowCountCombo) }
            separator()
            row { cell(panel) }
        }
        return JBScrollPane(panel).apply {
            minimumSize = Dimension(700, 450)
            preferredSize = Dimension(700, 450)
            verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        }
    }

    private fun updateCenterPanel() {
        // Очищаем старое содержимое
        panel.removeAll()

        // Пересоздаем содержимое centerPanel в зависимости от выбранного элемента
        val newContent = panel {
            initColumns()
        }

        // Добавляем новое содержимое и перерисовываем панель
        panel.add(newContent)
        panel.revalidate()
        panel.repaint()
    }

    private fun updatePanel(panel: JPanel, neededElements: MutableList<ComposeElements>) {
        // Очищаем старое содержимое
        panel.removeAll()

        // Пересоздаем содержимое centerPanel в зависимости от выбранного элемента
        val newContent = panel {
            row {
                repeat(neededElements.size) { index ->
                    val elComboBox = ComboBox(ComposeElements.entries.map { it.title }.toTypedArray())
                    elComboBox.addActionListener {
                        neededElements[index] = ComposeElements.getElementOrDefault(elComboBox.selectedItem as String)
                    }
                    elComboBox.selectedItem = neededElements[index].title
                    cell(elComboBox)
                }
            }
        }

        // Добавляем новое содержимое и перерисовываем панель
        panel.add(newContent)
        panel.revalidate()
        panel.repaint()
    }

    private fun Panel.initColumns() {
        repeat(generatorModel.rowCount) { index ->
            val neededElements = generatorModel.elements[index]
            val panel = JPanel()

            row("Количество элементов в ${index + 1} секции:") {
                val colComboBox = ComboBox(arrayOf(1, 2, 3))
                colComboBox.addActionListener {
                    updateRowModel(index, colComboBox.selectedItem as Int)
                    updatePanel(panel, neededElements)
                }
                colComboBox.selectedItem = neededElements.size
                cell(colComboBox)
            }
            row {
                cell(panel)
            }
        }
    }

    private fun initGeneratorModel() {
        val selectedItem = rowCountCombo.selectedItem as Int
        if (generatorModel.rowCount == selectedItem) return
        generatorModel.rowCount = selectedItem
        var elements = generatorModel.elements
        when {
            elements.isEmpty() -> elements =
                MutableList(generatorModel.rowCount) { mutableListOf(ComposeElements.TEXT) }

            elements.size < selectedItem -> {
                repeat(selectedItem - elements.size) {
                    elements.add(mutableListOf(ComposeElements.TEXT))
                }
            }

            elements.size > selectedItem -> {
                repeat(elements.size - selectedItem) {
                    elements.removeLast()
                }
            }
        }
    }

    private fun updateRowModel(index: Int, selectedItem: Int) {
        val elementsInRow = generatorModel.elements[index]
        when {
            elementsInRow.size > selectedItem -> {
                repeat(elementsInRow.size - selectedItem) {
                    elementsInRow.removeLast()
                }
            }

            elementsInRow.size < selectedItem -> {
                repeat(selectedItem - elementsInRow.size) {
                    elementsInRow.add(ComposeElements.TEXT)
                }
            }
        }
    }

    override fun doOKAction() {
        val packageName = packageNameField.text
        val composableName = composableNameField.text
        val content = generateContent(packageName, composableName, generatorModel)
        val error = DirectoryHelper.generateScreenOrError(project, packageName, composableName, content)
        if (error != null) {
            Messages.showMessageDialog(project, error, "Error", Messages.getErrorIcon())
        } else {
            super.doOKAction()
        }
    }
}
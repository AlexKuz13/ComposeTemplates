package com.alexkuz.composetemplates.generator.utils

import com.alexkuz.composetemplates.generator.model.ComposeElements
import com.alexkuz.composetemplates.generator.model.TemplateGeneratorModel
import com.alexkuz.composetemplates.utils.getComposeCommonImports

fun generateContent(
    packageName: String,
    composableName: String,
    generatorModel: TemplateGeneratorModel
): String {
    val content = StringBuilder().apply {
        appendLine("package $packageName")
        appendLine()
        appendLine(getImports(generatorModel))
        appendLine(getContent(composableName, generatorModel))
        appendLine()
        appendLine(getContentInternal(composableName, generatorModel))
        appendLine()
        appendLine(getPreview(composableName, generatorModel))
    }
    return content.toString().trimIndent()
}

private fun getImports(generatorModel: TemplateGeneratorModel): String {
    val isRowNeeded = generatorModel.elements.any { it.size > 1 }
    val elements = generatorModel.elements.flatten().toSet()
    val imports = mutableListOf(
        "androidx.compose.ui.Alignment",
        "androidx.compose.foundation.layout.Arrangement",
    )

    if (isRowNeeded) {
        imports.add("androidx.compose.foundation.layout.Row")
    }

    if (elements.any { it == ComposeElements.CHECKBOX }) {
        imports.add("androidx.compose.material3.ExperimentalMaterial3Api")
    }

    elements.map { el ->
        when (el) {
            ComposeElements.TEXT -> {
                listOf("androidx.compose.material3.Text")
            }

            ComposeElements.BUTTON -> {
                buildList {
                    add("androidx.compose.material3.Button")
                    add("androidx.compose.foundation.shape.RoundedCornerShape")
                    if (elements.none { it == ComposeElements.TEXT }) add("androidx.compose.material3.Text")
                }
            }

            ComposeElements.FAB -> {
                buildList {
                    add("androidx.compose.material3.FloatingActionButton")
                    if (elements.none { it == ComposeElements.TEXT }) add("androidx.compose.material3.Text")
                }
            }

            ComposeElements.TEXT_FIELD -> {
                buildList {
                    add("androidx.compose.material3.TextField")
                    if (elements.none { it == ComposeElements.TEXT || it == ComposeElements.FAB }) add("androidx.compose.material3.Text")
                }
            }

            ComposeElements.CHECKBOX -> {
                listOf("androidx.compose.material3.Checkbox")
            }

            ComposeElements.SWITCH -> {
                listOf("androidx.compose.material3.Switch")
            }

            ComposeElements.IMAGE -> {
                listOf(
                    "androidx.compose.foundation.Image",
                    "android.graphics.Bitmap",
                    "androidx.compose.ui.graphics.asImageBitmap",
                )
            }

            ComposeElements.SLIDER -> {
                listOf(
                    "androidx.compose.material3.Slider",
                    "androidx.compose.foundation.layout.width",
                    "android.graphics.Bitmap",
                    "androidx.compose.ui.graphics.asImageBitmap",
                )
            }
        }
    }.forEach(imports::addAll)

    return getComposeCommonImports(imports)
}

private fun getContent(composableName: String, generatorModel: TemplateGeneratorModel): String {
    val screenCallParameters = getScreenCallParameters(generatorModel)
    return """
@Composable
fun $composableName() {
    /**
    Here you can write code related to the viewModel, various states, side effects.
     */
    $composableName(
        $screenCallParameters
    )
}
""".trimIndent()
}

private fun getPreview(composableName: String, generatorModel: TemplateGeneratorModel): String {
    val screenCallParameters = getScreenCallParameters(generatorModel)
    return """
@Preview(showBackground = true)
@Composable
private fun ${composableName}Preview() {
    //YourAppTheme {
    $composableName(
        $screenCallParameters
    )
    //}
}
""".trimIndent()
}

private fun getScreenCallParameters(generatorModel: TemplateGeneratorModel): String {
    val parameters = StringBuilder()
    parameters.appendLine("modifier = Modifier,")
    generatorModel.elements.mapIndexed { rowIdx, row ->
        row.mapIndexed { colIdx, el ->
            when (el) {
                ComposeElements.BUTTON -> {
                    "onBtnClick$rowIdx$colIdx = {},"
                }

                ComposeElements.FAB -> {
                    "onFabClick$rowIdx$colIdx = {},"
                }

                ComposeElements.TEXT_FIELD -> {
                    "text$rowIdx$colIdx = \"\",\n\t\tonTextChange$rowIdx$colIdx = {},"
                }

                ComposeElements.CHECKBOX -> {
                    "checkValue$rowIdx$colIdx = true,\n\t\tonCheckedChange$rowIdx$colIdx = {},"
                }

                ComposeElements.SWITCH -> {
                    "switchValue$rowIdx$colIdx = true,\n\t\tonSwitchedChange$rowIdx$colIdx = {},"
                }

                ComposeElements.SLIDER -> {
                    "sliderValue$rowIdx$colIdx = 0f,\n\t\tonValueChange$rowIdx$colIdx = {},"
                }

                else -> ""
            }
        }
    }
        .flatten()
        .filter { it.isNotBlank() }
        .map { "\t\t$it" }
        .forEach(parameters::appendLine)
    return parameters.toString().trimIndent()
}

private fun getContentInternal(composableName: String, generatorModel: TemplateGeneratorModel): String {
    val screenParameters = getScreenParameters(generatorModel)
    val elementsContent = getElementsContent(generatorModel)
    val isNeedExpAnnotation = generatorModel.elements.flatten().any { it == ComposeElements.CHECKBOX }
    val annotation = if (isNeedExpAnnotation) "\n@OptIn(ExperimentalMaterial3Api::class)" else ""
    return """$annotation
@Composable
private fun $composableName(
    $screenParameters
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        $elementsContent
    }
}
""".trimIndent()
}

private fun getScreenParameters(generatorModel: TemplateGeneratorModel): String {
    val parameters = StringBuilder()
    parameters.appendLine("modifier: Modifier = Modifier,")
    generatorModel.elements.mapIndexed { rowIdx, row ->
        row.mapIndexed { colIdx, el ->
            when (el) {
                ComposeElements.BUTTON -> {
                    "onBtnClick$rowIdx$colIdx: () -> Unit,"
                }

                ComposeElements.FAB -> {
                    "onFabClick$rowIdx$colIdx: () -> Unit,"
                }

                ComposeElements.TEXT_FIELD -> {
                    "text$rowIdx$colIdx: String, // from state\n\tonTextChange$rowIdx$colIdx: (String) -> Unit,"
                }

                ComposeElements.CHECKBOX -> {
                    "checkValue$rowIdx$colIdx: Boolean, // from state\n\tonCheckedChange$rowIdx$colIdx: (Boolean) -> Unit,"
                }

                ComposeElements.SWITCH -> {
                    "switchValue$rowIdx$colIdx: Boolean, // from state\n\tonSwitchedChange$rowIdx$colIdx: (Boolean) -> Unit,"
                }

                ComposeElements.SLIDER -> {
                    "sliderValue$rowIdx$colIdx: Float, // from state\n\tonValueChange$rowIdx$colIdx: (Float) -> Unit,"
                }

                else -> ""
            }
        }
    }
        .flatten()
        .filter { it.isNotBlank() }
        .map { "\t$it" }
        .forEach(parameters::appendLine)
    return parameters.toString().trimIndent()
}

private fun getElementsContent(generatorModel: TemplateGeneratorModel): String {
    val content = StringBuilder()
    generatorModel.elements.forEachIndexed { rowIdx, row ->
        if (row.size > 1) {
            content.append(getRowContent(rowIdx, row))
        } else {
            content.append(row.first().mapToCompose("${rowIdx}0"))
        }
    }
    return content.toString().trimStart()
}

private fun getRowContent(rowIdx: Int, row: MutableList<ComposeElements>): String {
    val content = StringBuilder()
    row.forEachIndexed { idx, el ->
        content.append(el.mapToCompose("$rowIdx$idx"))
    }
    val formattedContent = content.trimStart().split("\n").joinToString("\n") { "    $it" }
    return """
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
        $formattedContent
        }"""
}

private fun ComposeElements.mapToCompose(index: String): String {
    return when (this) {
        ComposeElements.TEXT -> """
        Text(
            text = "$title",
            fontSize = 16.sp,
        )"""

        ComposeElements.BUTTON -> """
        Button(
            onClick = onBtnClick$index,
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "$title",
                fontSize = 16.sp,
            )
        }"""

        ComposeElements.FAB -> """
        FloatingActionButton(
            onClick = onFabClick$index
        ) {
            Text("$title")
        }"""

        ComposeElements.TEXT_FIELD -> """
        TextField(
            value = text$index,
            onValueChange = onTextChange$index,
            singleLine = true,
        )"""

        ComposeElements.CHECKBOX -> """
        Checkbox(
            checked = checkValue$index,
            onCheckedChange = onCheckedChange$index,
        )"""

        ComposeElements.SWITCH -> """
        Switch(
            checked = switchValue$index,
            onCheckedChange = onSwitchedChange$index,
        )"""

        ComposeElements.IMAGE -> """
        Image(
            bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888).asImageBitmap(),
            contentDescription = "$title",
        )"""

        ComposeElements.SLIDER -> """
        Slider(
            modifier = Modifier.width(100.dp),
            value = sliderValue$index,
            onValueChange = onValueChange$index
        )"""
    }
}
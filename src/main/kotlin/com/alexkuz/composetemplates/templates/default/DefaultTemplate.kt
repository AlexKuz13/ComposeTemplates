package com.alexkuz.composetemplates.templates.default

import com.alexkuz.composetemplates.blocks.getButton
import com.alexkuz.composetemplates.blocks.getPreview
import com.alexkuz.composetemplates.blocks.getText
import com.alexkuz.composetemplates.utils.getComposeCommonImports
import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import com.android.tools.idea.wizard.template.renderIf

fun defaultTemplate(
    composableName: String,
    generatePreview: Boolean,
    packageName: String,
): String {
    val previewParameters = "onBtnClick = {}"
    val previewBlock = renderIf(generatePreview) { getPreview(composableName, previewParameters) }
    return """
package ${escapeKotlinIdentifier(packageName)}
        
${getDefaultTemplateImports()}

@Composable
fun $composableName() {
    /**
    Here you can write code related to the viewModel, various states, side effects.
     */

    $composableName(
        onBtnClick = {}
    )
}

@Composable
private fun $composableName(
    modifier: Modifier = Modifier,
    onBtnClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ${getText()}
        ${getButton()}
    }
}

$previewBlock
""".trimIndent()
}

private fun getDefaultTemplateImports() = getComposeCommonImports(
    listOf(
        "androidx.compose.ui.text.style.TextAlign",
        "androidx.compose.ui.Alignment",
        "androidx.compose.foundation.layout.Arrangement",
        "androidx.compose.material3.Text",
        "androidx.compose.material3.Button",
        "androidx.compose.foundation.shape.RoundedCornerShape",
    )
)
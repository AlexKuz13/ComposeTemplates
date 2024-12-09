package com.alexkuz.composetemplates.templates.settings

import com.alexkuz.composetemplates.templates.blocks.getDivider
import com.alexkuz.composetemplates.templates.blocks.getPreview
import com.alexkuz.composetemplates.templates.blocks.getText
import com.alexkuz.composetemplates.utils.getComposeCommonImports
import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import com.android.tools.idea.wizard.template.renderIf

fun settingsTemplate(
    composableName: String,
    generatePreview: Boolean,
    packageName: String,
): String {
    val screenCallParameters = getScreenCallParameters()
    val screenParameters = getScreenParameters()
    val previewBlock = renderIf(generatePreview) { getPreview(composableName, screenCallParameters) }
    val content = getSettingsContent()
    return """
package ${escapeKotlinIdentifier(packageName)}
        
${getSettingsTemplateImports()}

private val themes = listOf("System", "Light", "Dark")

@Composable
fun $composableName() {
    /**
    Here you can write code related to the viewModel, various states, side effects.
     */
    
    $composableName(
        $screenCallParameters
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun $composableName(
    $screenParameters
) {
    var selectedOption by remember { mutableStateOf(selectedTheme) }
    var isNotifyEnabled by remember { mutableStateOf(notifyEnabled) }
    var isSendAnalytics by remember { mutableStateOf(sendAnalytics) }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        $content
    }
}

$previewBlock
""".trimIndent()
}

private fun getScreenCallParameters() = """
    themes = themes, // constants
    selectedTheme = themes[1], // from state
    notifyEnabled = true, // from state
    sendAnalytics = true, // from state
    onEditProfile = {},
    onThemeSelected = {},
    onNotifyChanged = {},
    onSendAnalytics = {},
    onExit = {},
""".trimIndent()

private fun getScreenParameters() = """
    themes: List<String>,
    selectedTheme: String,
    notifyEnabled: Boolean,
    sendAnalytics: Boolean,
    modifier: Modifier = Modifier,
    onEditProfile: () -> Unit,
    onThemeSelected: (String) -> Unit,
    onNotifyChanged: (Boolean) -> Unit,
    onSendAnalytics: (Boolean) -> Unit,
    onExit: () -> Unit,
""".trimIndent()

private fun getSettingsTemplateImports(): String {
    val imports = mutableListOf(
        "androidx.compose.foundation.clickable",
        "androidx.compose.foundation.layout.Arrangement",
        "androidx.compose.foundation.layout.Row",
        "androidx.compose.material3.Checkbox",
        "androidx.compose.material3.Divider",
        "androidx.compose.material3.ExperimentalMaterial3Api",
        "androidx.compose.material3.RadioButton",
        "androidx.compose.material3.Switch",
        "androidx.compose.material3.Text",
        "androidx.compose.runtime.getValue",
        "androidx.compose.runtime.mutableStateOf",
        "androidx.compose.runtime.remember",
        "androidx.compose.runtime.setValue",
        "androidx.compose.ui.Alignment",
        "androidx.compose.ui.graphics.Color",
        "androidx.compose.ui.text.style.TextAlign",
    )
    return getComposeCommonImports(imports)
}

private fun getSettingsContent(): String {
    return """
        ${getText("Settings")}
        ${getClickableSettingsRow("onEditProfile()", "Edit profile", "Color.Black")}
        ${getDivider()}
        Text(
            text = "Select theme",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            fontSize = 18.sp,
        )
        themes.forEach { text ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        enabled = text != selectedOption,
                        onClick = {
                            selectedOption = text
                            onThemeSelected(text)
                        }
                    ),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {
                        if (text != selectedOption) {
                            selectedOption = text
                            onThemeSelected(text)
                        }
                    },
                )
                Text(text = text, fontSize = 16.sp)
            }
        }
        ${getDivider()}
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Enable notifications",
                fontSize = 18.sp
            )
            Switch(
                checked = isNotifyEnabled,
                onCheckedChange = {
                    isNotifyEnabled = it
                    onNotifyChanged(isNotifyEnabled)
                }
            )
        }
        ${getDivider()}
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = {
                        isSendAnalytics = !isSendAnalytics
                        onSendAnalytics(isSendAnalytics)
                    }
                )
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSendAnalytics,
                onCheckedChange = {
                    isSendAnalytics = it
                    onSendAnalytics(isSendAnalytics)
                }
            )
            Text(text = "Send Analytics", fontSize = 18.sp)
        }
        ${getDivider()}
        ${getClickableSettingsRow("onExit()", "Exit", "Color.Red")}
    """.trimIndent()
}

private fun getClickableSettingsRow(onClick: String, text: String, color: String) = """
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { $onClick }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$text",
            color = $color,
            fontSize = 18.sp,
        )
        Text(
            text = ">",
            color = $color,
            fontSize = 18.sp,
        )
    }
""".trimIndent()
package com.alexkuz.composetemplates.blocks

import com.android.tools.idea.wizard.template.renderIf

fun getTextField(
    value: String,
    onValueChange: String,
    placeholder: String,
    additionalFields: String = "",
): String {
    val addFields = renderIf(additionalFields.isNotBlank()) { additionalFields }
    return """
TextField(
    value = $value,
    onValueChange = { $onValueChange(it) },
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
    placeholder = {
        Text("$placeholder")
    },
    singleLine = true,
    $addFields
)
""".trimIndent()
}
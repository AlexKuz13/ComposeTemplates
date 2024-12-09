package com.alexkuz.composetemplates.templates.blocks

fun getButton(onClick: String = "onBtnClick", text: String = "Button") = getButtonInternal(onClick, text)

fun getOutlinedButton(onClick: String = "onBtnClick", text: String = "Button") = getButtonInternal(onClick, text, "Outlined")

private fun getButtonInternal(onClick: String, text: String, typeButton: String = "") = """
${typeButton}Button(
    onClick = $onClick,
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
    shape = RoundedCornerShape(8.dp)
) {
    Text(
        text = "$text",
        fontSize = 16.sp,
    )
}
""".trimIndent()
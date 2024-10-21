package com.alexkuz.composetemplates.blocks

fun getButton() = """
Button(
    onClick = onBtnClick,
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
    shape = RoundedCornerShape(8.dp)
) {
    Text(
        text = "Button",
        fontSize = 16.sp,
    )
}
""".trimIndent()
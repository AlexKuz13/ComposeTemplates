package com.alexkuz.composetemplates.templates.blocks

fun getText(text: String = "Example Text") = """
Text(
    text = "$text",
    modifier = Modifier
       .fillMaxWidth()
       .padding(16.dp),
    textAlign = TextAlign.Center,
    fontSize = 28.sp,
)
""".trimIndent()
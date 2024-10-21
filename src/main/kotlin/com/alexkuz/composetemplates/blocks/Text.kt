package com.alexkuz.composetemplates.blocks

fun getText() = """
Text(
    text = "Example Text",
    modifier = Modifier
       .fillMaxWidth()
       .padding(16.dp),
    textAlign = TextAlign.Center,
    fontSize = 28.sp,
)
""".trimIndent()
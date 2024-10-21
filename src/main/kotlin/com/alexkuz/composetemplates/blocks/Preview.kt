package com.alexkuz.composetemplates.blocks

fun getPreview(composableName: String, parameters: String) = """
@Preview(showBackground = true)
@Composable
private fun ${composableName}Preview() {
   //YourAppTheme {
      ${composableName}($parameters)
   //}
}
""".trimIndent()
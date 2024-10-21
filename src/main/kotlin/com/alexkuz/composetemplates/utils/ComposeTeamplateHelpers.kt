package com.alexkuz.composetemplates.utils

fun getComposeCommonImports(additionalImports: List<String>) = buildString {
    mutableListOf(
        "androidx.compose.runtime.Composable",
        "androidx.compose.ui.tooling.preview.Preview",
        "androidx.compose.ui.Modifier",
        "androidx.compose.ui.unit.dp",
        "androidx.compose.ui.unit.sp",
        "androidx.compose.foundation.layout.fillMaxSize",
        "androidx.compose.foundation.layout.fillMaxWidth",
        "androidx.compose.foundation.layout.padding",
        "androidx.compose.foundation.layout.Column",
        )
        .apply {
            addAll(additionalImports)
        }
        .map {
            append("import $it")
            appendLine()
        }
}
package com.alexkuz.composetemplates.generator.model

data class TemplateGeneratorModel(
    var rowCount: Int = 1,
    var elements: MutableList<MutableList<ComposeElements>> = mutableListOf(mutableListOf(ComposeElements.TEXT)),
)
package com.alexkuz.composetemplates.generator.model

enum class ComposeElements(val title: String) {
    BUTTON("Button"),
    CHECKBOX("Checkbox"),
    FAB("FAB"),
    IMAGE("Image"),
    SLIDER("Slider"),
    SWITCH("Switch"),
    TEXT("Text"),
    TEXT_FIELD("TextField");

    companion object {
        fun getElementOrDefault(title: String) = entries.find { it.title == title } ?: TEXT
    }
}
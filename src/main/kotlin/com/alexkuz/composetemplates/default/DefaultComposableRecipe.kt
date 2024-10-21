package com.alexkuz.composetemplates.default

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor

fun RecipeExecutor.defaultComposableRecipe(
    moduleData: ModuleTemplateData,
    composableName: String,
    generatePreview: Boolean,
    packageName: String,
) {
    val srcOut = moduleData.srcDir

    save(defaultTemplate(composableName, generatePreview, packageName), srcOut.resolve("$composableName.kt"))

    open(srcOut.resolve("$composableName.kt"))
}
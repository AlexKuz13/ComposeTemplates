package com.alexkuz.composetemplates.templates.tabs

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor

fun RecipeExecutor.tabsComposableRecipe(
    moduleData: ModuleTemplateData,
    composableName: String,
    generatePreview: Boolean,
    packageName: String,
) {
    val srcOut = moduleData.srcDir

    save(tabsTemplate(composableName, generatePreview, packageName), srcOut.resolve("$composableName.kt"))

    open(srcOut.resolve("$composableName.kt"))
}
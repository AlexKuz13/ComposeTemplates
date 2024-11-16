package com.alexkuz.composetemplates.templates.bottomsheet

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor

fun RecipeExecutor.bottomSheetComposableRecipe(
    moduleData: ModuleTemplateData,
    composableName: String,
    generatePreview: Boolean,
    packageName: String,
) {
    val srcOut = moduleData.srcDir

    save(bottomSheetTemplate(composableName, generatePreview, packageName), srcOut.resolve("$composableName.kt"))

    open(srcOut.resolve("$composableName.kt"))
}
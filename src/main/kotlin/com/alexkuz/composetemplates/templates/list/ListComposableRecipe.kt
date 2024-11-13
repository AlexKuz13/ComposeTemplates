package com.alexkuz.composetemplates.templates.list

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor

fun RecipeExecutor.listComposableRecipe(
    moduleData: ModuleTemplateData,
    composableName: String,
    generateSwipeRefresh: Boolean,
    generatePreview: Boolean,
    packageName: String,
) {
    val srcOut = moduleData.srcDir

    save(
        listTemplate(composableName, generateSwipeRefresh, generatePreview, packageName),
        srcOut.resolve("$composableName.kt")
    )

    open(srcOut.resolve("$composableName.kt"))
}
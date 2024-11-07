package com.alexkuz.composetemplates.templates.login

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor

fun RecipeExecutor.loginComposableRecipe(
    moduleData: ModuleTemplateData,
    composableName: String,
    generatePreview: Boolean,
    packageName: String,
    loginType: LoginType,
) {
    val srcOut = moduleData.srcDir

    save(loginTemplate(composableName, generatePreview, packageName, loginType), srcOut.resolve("$composableName.kt"))

    open(srcOut.resolve("$composableName.kt"))
}
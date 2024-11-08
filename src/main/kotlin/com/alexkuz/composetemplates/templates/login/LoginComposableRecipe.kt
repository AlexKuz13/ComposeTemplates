package com.alexkuz.composetemplates.templates.login

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor

/**
 * @param loginType if null register recipe, else login recipe
 */
fun RecipeExecutor.loginOrRegisterComposableRecipe(
    moduleData: ModuleTemplateData,
    composableName: String,
    generatePreview: Boolean,
    packageName: String,
    loginType: LoginType? = null,
) {
    val srcOut = moduleData.srcDir

    save(loginOrRegisterTemplate(composableName, generatePreview, packageName, loginType), srcOut.resolve("$composableName.kt"))

    open(srcOut.resolve("$composableName.kt"))
}
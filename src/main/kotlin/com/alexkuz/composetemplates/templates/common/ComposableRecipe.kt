package com.alexkuz.composetemplates.templates.common

import com.alexkuz.composetemplates.templates.bottomsheet.bottomSheetTemplate
import com.alexkuz.composetemplates.templates.calendar.calendarTemplate
import com.alexkuz.composetemplates.templates.default.defaultTemplate
import com.alexkuz.composetemplates.templates.list.listTemplate
import com.alexkuz.composetemplates.templates.login.loginOrRegisterTemplate
import com.alexkuz.composetemplates.templates.settings.settingsTemplate
import com.alexkuz.composetemplates.templates.tabs.tabsTemplate
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor

fun RecipeExecutor.composableRecipe(
    moduleData: ModuleTemplateData,
    composableName: String,
    generatePreview: Boolean,
    packageName: String,
    templateRecipe: TemplateRecipe,
) {
    val srcOut = moduleData.srcDir

    val template = when (templateRecipe) {
        is TemplateRecipe.DefaultComposableRecipe -> defaultTemplate(composableName, generatePreview, packageName)
        is TemplateRecipe.TabsComposableRecipe -> tabsTemplate(composableName, generatePreview, packageName)
        is TemplateRecipe.CalendarComposableRecipe -> calendarTemplate(composableName, generatePreview, packageName)
        is TemplateRecipe.SettingsComposableRecipe -> settingsTemplate(composableName, generatePreview, packageName)
        is TemplateRecipe.LoginComposableRecipe -> loginOrRegisterTemplate(
            composableName,
            generatePreview,
            packageName,
            templateRecipe.loginType,
        )

        is TemplateRecipe.BottomSheetComposableRecipe -> bottomSheetTemplate(
            composableName,
            generatePreview,
            packageName
        )

        is TemplateRecipe.ListComposableRecipe -> listTemplate(
            composableName,
            generatePreview,
            templateRecipe.generateSwipeRefresh,
            packageName
        )
    }
    save(template, srcOut.resolve("$composableName.kt"))

    open(srcOut.resolve("$composableName.kt"))
}
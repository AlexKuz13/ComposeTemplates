package com.alexkuz.composetemplates.templates.common

import com.alexkuz.composetemplates.templates.login.LoginType

sealed class TemplateRecipe {
    data object DefaultComposableRecipe : TemplateRecipe()
    data object TabsComposableRecipe : TemplateRecipe()
    data object BottomSheetComposableRecipe : TemplateRecipe()
    data object CalendarComposableRecipe : TemplateRecipe()
    data object SettingsComposableRecipe : TemplateRecipe()
    data class LoginComposableRecipe(val loginType: LoginType? = null) : TemplateRecipe()
    data class ListComposableRecipe(val generateSwipeRefresh: Boolean) : TemplateRecipe()
}
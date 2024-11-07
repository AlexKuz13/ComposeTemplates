package com.alexkuz.composetemplates.templates

import com.alexkuz.composetemplates.templates.default.DefaultComposableTemplate
import com.alexkuz.composetemplates.templates.login.LoginComposableTemplate
import com.android.tools.idea.wizard.template.Template
import com.android.tools.idea.wizard.template.WizardTemplateProvider

class ComposableTemplateProvider: WizardTemplateProvider() {
    override fun getTemplates(): List<Template> {
        return listOf(
            DefaultComposableTemplate,
            LoginComposableTemplate
        )
    }
}
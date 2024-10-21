package com.alexkuz.composetemplates.default

import com.android.tools.idea.wizard.template.Template
import com.android.tools.idea.wizard.template.WizardTemplateProvider

class ComposableTemplateProvider: WizardTemplateProvider() {
    override fun getTemplates(): List<Template> {
        return listOf(DefaultComposableTemplate)
    }
}
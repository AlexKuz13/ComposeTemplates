package com.alexkuz.composeteamplates

import com.alexkuz.composeteamplates.recipes.customActivity.src.CustomActivityTemplate
import com.android.tools.idea.wizard.template.Template
import com.android.tools.idea.wizard.template.WizardTemplateProvider

class CustomWizardTemplateProvider: WizardTemplateProvider() {
    override fun getTemplates(): List<Template> {
        return listOf(CustomActivityTemplate)
    }
}
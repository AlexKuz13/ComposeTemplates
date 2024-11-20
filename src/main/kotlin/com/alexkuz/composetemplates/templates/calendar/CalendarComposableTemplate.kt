package com.alexkuz.composetemplates.templates.calendar

import com.alexkuz.composetemplates.templates.common.TemplateRecipe.CalendarComposableRecipe
import com.alexkuz.composetemplates.templates.common.composableRecipe
import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.defaultPackageNameParameter

object CalendarComposableTemplate : Template {

    private val packageName = defaultPackageNameParameter

    private val generatePreview = booleanParameter {
        name = "Add Preview for Composable"
        default = true
        help = "If true, adds preview for Composable fun"
    }

    private val composableName = stringParameter {
        name = "Composable Name"
        default = "MainScreen"
        help = "The name of the Composable fun"
        constraints = listOf(Constraint.KOTLIN_FUNCTION, Constraint.NONEMPTY)
    }

    override val category: Category
        get() = Category.Compose
    override val constraints: Collection<TemplateConstraint>
        get() = listOf(TemplateConstraint.Compose)
    override val description: String
        get() = "Calendar template with DatePickerDialog"
    override val documentationUrl: String?
        get() = null
    override val formFactor: FormFactor
        get() = FormFactor.Mobile
    override val minSdk: Int
        get() = 23
    override val name: String
        get() = "Calendar Template"
    override val recipe: Recipe
        get() = {
            composableRecipe(
                it as ModuleTemplateData,
                composableName.value,
                generatePreview.value,
                packageName.value,
                CalendarComposableRecipe
            )
        }
    override val uiContexts: Collection<WizardUiContext>
        get() = listOf(WizardUiContext.MenuEntry)
    override val useGenericInstrumentedTests: Boolean
        get() = false
    override val useGenericLocalTests: Boolean
        get() = false
    override val widgets: Collection<Widget<*>>
        get() = listOf(
            TextFieldWidget(composableName),
            CheckBoxWidget(generatePreview),
            PackageNameWidget(packageName)
        )

    override fun thumb() = Thumb.NoThumb
}
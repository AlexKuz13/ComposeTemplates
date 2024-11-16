package com.alexkuz.composetemplates.templates.bottomsheet

import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.defaultPackageNameParameter

object BottomSheetComposableTemplate : Template {

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
        get() = "Template with topBar and bottomSheet"
    override val documentationUrl: String?
        get() = null
    override val formFactor: FormFactor
        get() = FormFactor.Mobile
    override val minSdk: Int
        get() = 23
    override val name: String
        get() = "TopBar and BottomSheet Template"
    override val recipe: Recipe
        get() = {
            bottomSheetComposableRecipe(
                it as ModuleTemplateData,
                composableName.value,
                generatePreview.value,
                packageName.value,
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
            PackageNameWidget(packageName),
        )

    override fun thumb() = Thumb.NoThumb
}
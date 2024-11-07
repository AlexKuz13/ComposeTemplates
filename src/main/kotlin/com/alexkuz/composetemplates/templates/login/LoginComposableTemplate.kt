package com.alexkuz.composetemplates.templates.login

import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.defaultPackageNameParameter

object LoginComposableTemplate: Template {

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

    private val loginType = enumParameter<LoginType> {
        name = "Login type"
        default = LoginType.EMAIL_AND_PASSWORD
        help = "if EMAIL_AND_PASSWORD is selected, then 2 input fields and the possibility of registration will be generated\n" +
                "\n" +
                "if PHONE is selected, 1 input field will be generated"
    }

    override val category: Category
        get() = Category.Compose
    override val constraints: Collection<TemplateConstraint>
        get() = listOf(TemplateConstraint.Compose)
    override val description: String
        get() = "Login template with 2 different configurations"
    override val documentationUrl: String?
        get() = null
    override val formFactor: FormFactor
        get() = FormFactor.Mobile
    override val minSdk: Int
        get() = 23
    override val name: String
        get() = "Login Template"
    override val recipe: Recipe
        get() = {
            loginComposableRecipe(
                it as ModuleTemplateData,
                composableName.value,
                generatePreview.value,
                packageName.value,
                loginType.value,
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
            EnumWidget(loginType)
        )

    override fun thumb() = Thumb.NoThumb
}
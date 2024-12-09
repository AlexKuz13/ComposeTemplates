package com.alexkuz.composetemplates.templates.login

import com.alexkuz.composetemplates.templates.blocks.*
import com.alexkuz.composetemplates.utils.getComposeCommonImports
import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import com.android.tools.idea.wizard.template.renderIf

fun loginOrRegisterTemplate(
    composableName: String,
    generatePreview: Boolean,
    packageName: String,
    loginType: LoginType?,
): String {
    val screenCallParameters = getScreenCallParameters(loginType)
    val screenParameters = getScreenParameters(loginType)
    val previewBlock = renderIf(generatePreview) { getPreview(composableName, screenCallParameters) }
    val content = when (loginType) {
        LoginType.EMAIL_AND_PASSWORD -> getEmailAndPasswordContent()
        LoginType.PHONE -> getPhoneContent()
        null -> getRegisterContent()
    }
    return """
package ${escapeKotlinIdentifier(packageName)}
        
${getLoginTemplateImports(loginType)}

@Composable
fun $composableName() {
    /**
    Here you can write code related to the viewModel, various states, side effects.
     */
    
    $composableName(
        $screenCallParameters
    )
}

@Composable
private fun $composableName(
    $screenParameters
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        $content
    }
}

$previewBlock
""".trimIndent()
}

private fun getScreenCallParameters(loginType: LoginType?): String {
    return when (loginType) {
        LoginType.EMAIL_AND_PASSWORD -> """
            email = "", // from state
            password = "", // from state
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            onRegisterClick = {},
        """.trimIndent()

        LoginType.PHONE -> """
            phone = "", // from state
            onPhoneChange = {},
            onSendClick = {},
            onLoginClick = {},
        """.trimIndent()

        null -> """
            email = "", // from state
            name = "", // from state
            password = "", // from state
            repeatPassword = "", // from state
            onEmailChange = {},
            onNameChange = {},
            onPasswordChange = {},
            onRepeatPasswordChange = {},
            onLoginClick = {},
            onRegisterClick = {}, 
        """.trimIndent()
    }
}

private fun getScreenParameters(loginType: LoginType?): String {
    return when (loginType) {
        LoginType.EMAIL_AND_PASSWORD -> """
            email: String,
            password: String,
            modifier: Modifier = Modifier,
            onEmailChange: (String) -> Unit,
            onPasswordChange: (String) -> Unit,
            onLoginClick: () -> Unit,
            onRegisterClick: () -> Unit,
        """.trimIndent()

        LoginType.PHONE -> """
            phone: String,
            modifier: Modifier = Modifier,
            onPhoneChange: (String) -> Unit,
            onSendClick: () -> Unit,
            onLoginClick: () -> Unit,
        """.trimIndent()

        null -> """
            email: String,
            name: String,
            password: String,
            repeatPassword: String,
            modifier: Modifier = Modifier,
            onEmailChange: (String) -> Unit,
            onNameChange: (String) -> Unit,
            onPasswordChange: (String) -> Unit,
            onRepeatPasswordChange: (String) -> Unit,
            onLoginClick: () -> Unit,
            onRegisterClick: () -> Unit,
        """.trimIndent()
    }
}

private fun getLoginTemplateImports(loginType: LoginType?): String {
    val imports = mutableListOf(
        "androidx.compose.ui.text.style.TextAlign",
        "androidx.compose.ui.Alignment",
        "androidx.compose.foundation.layout.Arrangement",
        "androidx.compose.material3.Text",
        "androidx.compose.material3.Button",
        "androidx.compose.foundation.shape.RoundedCornerShape",
        "androidx.compose.material3.TextField",
    )
    when (loginType) {
        LoginType.EMAIL_AND_PASSWORD, null -> {
            imports.addAll(
                listOf(
                    "androidx.compose.ui.text.input.PasswordVisualTransformation",
                    "androidx.compose.runtime.remember",
                    "androidx.compose.foundation.layout.Spacer",
                    "androidx.compose.foundation.layout.width",
                    "androidx.compose.foundation.layout.Row",
                    "androidx.compose.foundation.interaction.MutableInteractionSource",
                    "androidx.compose.foundation.clickable",
                )
            )
        }

        LoginType.PHONE -> {
            imports.add("androidx.compose.material.OutlinedButton")
        }
    }
    return getComposeCommonImports(imports)
}

private fun getEmailAndPasswordContent(): String {
    return """
        ${getText("Login")}
        ${getTextField("email", "onEmailChange", "Email")}
        ${
        getTextField(
            value = "password",
            onValueChange = "onPasswordChange",
            placeholder = "Password",
            additionalFields = "visualTransformation = PasswordVisualTransformation()"
        )
    }
        ${getButton("onLoginClick", "Login")}
        Row {
            Text(
                text = "Don't have an account?",
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Sign Up",
                fontSize = 16.sp,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onRegisterClick
                ),
            )
        }
    """.trimIndent()
}

private fun getPhoneContent(): String {
    return """
        ${getText("Login")}
        ${getTextField("phone", "onPhoneChange", "+7")}
        ${getButton("onSendClick", "Send Code")}
        ${getOutlinedButton("onLoginClick", "Login with password")}
    """.trimIndent()
}

private fun getRegisterContent(): String {
    return """
        ${getText("Register")}
        ${getTextField("email", "onEmailChange", "Email")}
        ${getTextField("name", "onNameChange", "Name")}
        ${
        getTextField(
            value = "password",
            onValueChange = "onPasswordChange",
            placeholder = "Password",
            additionalFields = "visualTransformation = PasswordVisualTransformation()"
        )
    }
    ${
        getTextField(
            value = "repeatPassword",
            onValueChange = "onRepeatPasswordChange",
            placeholder = "Repeat password",
            additionalFields = "visualTransformation = PasswordVisualTransformation()"
        )
    }
        ${getButton("onRegisterClick", "Register")}
        Row {
            Text(
                text = "Already have an account?",
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Login",
                fontSize = 16.sp,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onLoginClick
                ),
            )
        }
    """.trimIndent()
}
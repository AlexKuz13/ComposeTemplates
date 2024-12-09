package com.alexkuz.composetemplates.templates.calendar

import com.alexkuz.composetemplates.templates.blocks.getButton
import com.alexkuz.composetemplates.templates.blocks.getPreview
import com.alexkuz.composetemplates.templates.blocks.getText
import com.alexkuz.composetemplates.utils.getArgSymbol
import com.alexkuz.composetemplates.utils.getComposeCommonImports
import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import com.android.tools.idea.wizard.template.renderIf

fun calendarTemplate(
    composableName: String,
    generatePreview: Boolean,
    packageName: String,
): String {
    val screenParameters = "onDateSelected = {}"
    val previewBlock = renderIf(generatePreview) { getPreview(composableName, screenParameters) }
    return """
package ${escapeKotlinIdentifier(packageName)}
        
${getCalendarTemplateImports()}

@Composable
fun $composableName() {
    /**
    Here you can write code related to the viewModel, various states, side effects.
     */

    $composableName(
        $screenParameters
    )
}

@Composable
private fun $composableName(
    modifier: Modifier = Modifier,
    onDateSelected: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ${getText("Selected date: ${getArgSymbol()}text")}
        ${getButton(getButtonOnClick(), "Select Date")}
    }
}

${getShowDatePicker()}

${getShowDatePickerInternal()}

$previewBlock
""".trimIndent()
}

private fun getCalendarTemplateImports() = getComposeCommonImports(
    listOf(
        "androidx.compose.ui.text.style.TextAlign",
        "androidx.compose.ui.Alignment",
        "androidx.compose.foundation.layout.Arrangement",
        "androidx.compose.material3.Text",
        "androidx.compose.material3.Button",
        "androidx.compose.foundation.shape.RoundedCornerShape",
        "android.content.Context",
        "androidx.compose.runtime.getValue",
        "androidx.compose.runtime.mutableStateOf",
        "androidx.compose.runtime.remember",
        "androidx.compose.runtime.setValue",
        "androidx.compose.ui.platform.LocalContext",
        "java.text.SimpleDateFormat",
        "java.time.LocalDateTime",
        "java.time.format.DateTimeFormatter",
        "java.util.Calendar",
        "java.util.Locale",
        "android.app.DatePickerDialog",
        "android.widget.DatePicker",
    )
)

private fun getButtonOnClick() = """
    {
        showDatePicker(context) { date ->
            text = date
            onDateSelected(date)
        }
    }
""".trimIndent()

private fun getShowDatePicker() = """
private fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    showDatePicker(
        context,
        Calendar.getInstance()
    ) {
        val formattedDate = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm",
            Locale("ru")
        ).format(it.timeInMillis)
        val localDateTime = LocalDateTime.parse(formattedDate)
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale("ru"))
        onDateSelected(localDateTime?.format(formatter).orEmpty())
    }
}
""".trimIndent()

private fun getShowDatePickerInternal() = """
fun showDatePicker(
    context: Context,
    calendar: Calendar,
    onDateChange: (Calendar) -> Unit
) {
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // use your styles for DatePicker
    DatePickerDialog(
        context,
        { datePicker: DatePicker, _, _, _ ->
            calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
            onDateChange(calendar)
        }, year, month, day
    ).apply {
        datePicker.minDate = Calendar.getInstance().timeInMillis
        show()
    }
}
""".trimIndent()
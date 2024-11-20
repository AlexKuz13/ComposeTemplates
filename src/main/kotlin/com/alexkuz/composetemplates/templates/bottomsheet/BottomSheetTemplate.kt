package com.alexkuz.composetemplates.templates.bottomsheet

import com.alexkuz.composetemplates.blocks.getButton
import com.alexkuz.composetemplates.blocks.getPreview
import com.alexkuz.composetemplates.blocks.getText
import com.alexkuz.composetemplates.utils.getComposeCommonImports
import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import com.android.tools.idea.wizard.template.renderIf

fun bottomSheetTemplate(
    composableName: String,
    generatePreview: Boolean,
    packageName: String,
): String {
    val screenCallParameters = getScreenCallParameters()
    val screenParameters = getScreenParameters()
    val previewParameters = getPreviewParameters()
    val previewBlock = renderIf(generatePreview) { getPreviewInternal(composableName, previewParameters) }
    val content = getContent()
    val topBar = getTopBar()
    val innerContent = getInnerContent()
    val bottomSheet = getBottomSheet()
    return """
package ${escapeKotlinIdentifier(packageName)}
        
${getBottomSheetTemplateImports()}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun $composableName() {
    /**
    Here you can write code related to the viewModel, various states, side effects.
     */
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    
    $composableName(
        $screenCallParameters
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun $composableName(
    $screenParameters
) {
    $content
}

$topBar

$innerContent

$bottomSheet

$previewBlock
""".trimIndent()
}

private fun getPreviewInternal(composableName: String, parameters: String) = """
@OptIn(ExperimentalMaterialApi::class)
${getPreview(composableName, parameters)}
""".trimIndent()

private fun getScreenCallParameters() = """
    scaffoldState = scaffoldState,
    onOpenClick = {
        scope.launch {
            scaffoldState.bottomSheetState.expand()
        }
    },
    onCloseClick = {
        scope.launch {
            scaffoldState.bottomSheetState.collapse()
        }
    },
""".trimIndent()

private fun getScreenParameters() = """
    scaffoldState: BottomSheetScaffoldState,
    modifier: Modifier = Modifier,
    onOpenClick: () -> Unit,
    onCloseClick: () -> Unit,
""".trimIndent()

private fun getPreviewParameters() = """
    scaffoldState = rememberBottomSheetScaffoldState(),
    onOpenClick = {},
    onCloseClick = {},
""".trimIndent()

private fun getBottomSheetTemplateImports(): String {
    val imports = mutableListOf(
        "androidx.compose.ui.text.style.TextAlign",
        "androidx.compose.ui.Alignment",
        "androidx.compose.foundation.layout.Arrangement",
        "androidx.compose.material3.Text",
        "androidx.compose.material3.Button",
        "androidx.compose.foundation.shape.RoundedCornerShape",
        "androidx.compose.foundation.layout.WindowInsets",
        "androidx.compose.foundation.layout.asPaddingValues",
        "androidx.compose.foundation.layout.statusBars",
        "androidx.compose.material.ExperimentalMaterialApi",
        "androidx.compose.foundation.layout.navigationBars",
        "androidx.compose.material.BottomSheetScaffold",
        "androidx.compose.material.BottomSheetScaffoldState",
        "androidx.compose.material.Surface",
        "androidx.compose.material.rememberBottomSheetScaffoldState",
        "androidx.compose.runtime.rememberCoroutineScope",
        "kotlinx.coroutines.launch",
        "androidx.compose.foundation.layout.fillMaxHeight",
    )
    return getComposeCommonImports(imports)
}

private fun getContent() = """
BottomSheetScaffold(
    scaffoldState = scaffoldState,
    modifier = modifier,
    sheetContent = { BottomSheet() },
    sheetPeekHeight = 0.dp,
    topBar = { TopBar() },
) {
    Content(
        modifier = modifier,
        onOpenClick = onOpenClick,
        onCloseClick = onCloseClick,
    )
}
""".trimIndent()

private fun getTopBar() = """
@Composable
private fun TopBar() {
    Surface {
        Text(
            text = "TopBar",
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    top = WindowInsets.statusBars
                        .asPaddingValues()
                        .calculateTopPadding(),
                    end = 16.dp,
                    bottom = 16.dp
                ),
            fontSize = 20.sp,
        )
    }
}
""".trimIndent()

private fun getInnerContent() = """
@Composable
private fun Content(
    modifier: Modifier,
    onOpenClick: () -> Unit,
    onCloseClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        ${getButton("onOpenClick", "Open BottomSheet")}
        ${getButton("onCloseClick", "Close BottomSheet")}
    }
}
""".trimIndent()

private fun getBottomSheet() = """
@Composable
private fun BottomSheet() {
    Column(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .padding(WindowInsets.navigationBars.asPaddingValues()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ${getText("BottomSheet")}
    }
}
""".trimIndent()
package com.alexkuz.composetemplates.templates.tabs

import com.alexkuz.composetemplates.blocks.getPreview
import com.alexkuz.composetemplates.blocks.getText
import com.alexkuz.composetemplates.utils.getComposeCommonImports
import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import com.android.tools.idea.wizard.template.renderIf

fun tabsTemplate(
    composableName: String,
    generatePreview: Boolean,
    packageName: String,
): String {
    val screenParameters = "Modifier"
    val previewBlock = renderIf(generatePreview) { getPreview(composableName, screenParameters) }
    return """
package ${escapeKotlinIdentifier(packageName)}
        
${getDefaultTemplateImports()}

class TabsItem(
    val title: String,
    val screen: @Composable () -> Unit,
)

@Composable
fun $composableName() {
    /**
    Here you can write code related to the viewModel, various states, side effects.
     */

    $composableName(
        $screenParameters
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun $composableName(
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    
    val tabs = arrayListOf(
        ${getTabItem(1)},
        ${getTabItem(2)},
        ${getTabItem(3)},
    )
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues()),
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(index) }
                    },
                    text = { Text(text = tab.title, fontSize = 20.sp) }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            count = tabs.size
        ) { index ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                tabs[index].screen()
            }
        }
    }
}

$previewBlock
""".trimIndent()
}

private fun getDefaultTemplateImports() = getComposeCommonImports(
    listOf(
        "androidx.compose.ui.text.style.TextAlign",
        "androidx.compose.foundation.layout.Arrangement",
        "androidx.compose.material3.Text",
        "com.google.accompanist.pager.ExperimentalPagerApi",
        "androidx.compose.runtime.rememberCoroutineScope",
        "com.google.accompanist.pager.rememberPagerState",
        "kotlinx.coroutines.launch",
        "androidx.compose.material3.Tab",
        "androidx.compose.material3.TabRow",
        "com.google.accompanist.pager.HorizontalPager",
        "androidx.compose.foundation.layout.WindowInsets",
        "androidx.compose.foundation.layout.asPaddingValues",
        "androidx.compose.foundation.layout.statusBars",
    )
)

private fun getTabItem(number: Int) = """
TabsItem("Tab $number") {
    ${getText("Content $number")}
}
""".trimIndent()
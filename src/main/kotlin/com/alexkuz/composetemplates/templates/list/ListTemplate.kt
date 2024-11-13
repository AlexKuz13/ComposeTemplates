package com.alexkuz.composetemplates.templates.list

import com.alexkuz.composetemplates.blocks.*
import com.alexkuz.composetemplates.utils.getComposeCommonImports
import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import com.android.tools.idea.wizard.template.renderIf

fun listTemplate(
    composableName: String,
    generateSwipeRefresh: Boolean,
    generatePreview: Boolean,
    packageName: String,
): String {
    val screenCallParameters = getScreenCallParameters(generateSwipeRefresh)
    val screenParameters = getScreenParameters(generateSwipeRefresh)
    val previewParameters = getPreviewParameters(generateSwipeRefresh)
    val isRefreshingBlock = renderIf(generateSwipeRefresh) { getIsRefreshingBlock() }
    val previewBlock = renderIf(generatePreview) { getPreview(composableName, previewParameters) }
    val content = if (generateSwipeRefresh) getSwipeRefreshBlock() else getListBlock()

    return """
package ${escapeKotlinIdentifier(packageName)}
        
${getDefaultTemplateImports(generateSwipeRefresh)}

class Item(
    val id: Int,
    val title: String,
    val subtitle: String,
)

private fun getItems(): List<Item> { // for example
    val items = mutableListOf<Item>()
    repeat(15) { index ->
        items.add(Item(index, "Title ${getArgSymbol()}index", "Subtitle ${getArgSymbol()}index"))
    }
    return items
}

@Composable
fun $composableName() {
    /**
    Here you can write code related to the viewModel, various states, side effects.
     */
    $isRefreshingBlock
    
    $composableName(
        $screenCallParameters
    )
}

@Composable
private fun $composableName(
    $screenParameters
) {
    $content
}

${getItemBlock()}

$previewBlock
""".trimIndent()
}

private fun getScreenCallParameters(generateSwipeRefresh: Boolean): String {
    return if (generateSwipeRefresh) {
        """
            items = getItems(),
            isRefreshing = isRefreshing, // from state
            onItemClick = {},
            onRefresh = { isRefreshing = true },
        """.trimIndent()
    } else {
        """
           items = getItems(),
           onItemClick = {},
        """.trimIndent()
    }
}

private fun getScreenParameters(generateSwipeRefresh: Boolean): String {
    return if (generateSwipeRefresh) {
        """
            items: List<Item>,
            isRefreshing: Boolean,
            modifier: Modifier = Modifier,
            onItemClick: (Int) -> Unit,
            onRefresh: () -> Unit,
        """.trimIndent()
    } else {
        """
            items: List<Item>,
            modifier: Modifier = Modifier,
            onItemClick: (Int) -> Unit,
        """.trimIndent()
    }
}

private fun getPreviewParameters(generateSwipeRefresh: Boolean): String {
    return if (generateSwipeRefresh) {
        """
            items = getItems(),
            isRefreshing = false, // from state
            onItemClick = {},
            onRefresh = {},
        """.trimIndent()
    } else {
        """
           items = getItems(),
           onItemClick = {},
        """.trimIndent()
    }
}

private fun getIsRefreshingBlock() = """
    var isRefreshing: Boolean by remember { mutableStateOf(false) }

    if (isRefreshing) {
        LaunchedEffect(Unit) {
            delay(1000L)
            isRefreshing = false
        }
    }
""".trimIndent()

private fun getSwipeRefreshBlock() = """
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        indicatorPadding = WindowInsets.statusBars.asPaddingValues(),
        onRefresh = onRefresh,
    ) {
        ${getListBlock()}
    }
""".trimIndent()

private fun getListBlock() = """
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = WindowInsets.statusBars.asPaddingValues()
    ) {
        items(items = items, key = { item -> item.id }) { item ->
            Item(
                item = item,
                onItemClick = onItemClick,
            )
        }
    }
""".trimIndent()

private fun getItemBlock() = """
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Item(
        item: Item,
        onItemClick: (Int) -> Unit,
    ) {
        Card(
            onClick = { onItemClick(item.id) },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Text(
                text = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                fontSize = 20.sp,
            )
            Text(
                text = item.subtitle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                fontSize = 16.sp,
            )
        }
    }
""".trimIndent()

private fun getDefaultTemplateImports(generateSwipeRefresh: Boolean): String {
    val imports = mutableListOf(
        "androidx.compose.foundation.layout.WindowInsets",
        "androidx.compose.foundation.layout.asPaddingValues",
        "androidx.compose.foundation.layout.statusBars",
        "androidx.compose.foundation.lazy.LazyColumn",
        "androidx.compose.foundation.lazy.items",
        "androidx.compose.material3.Card",
        "androidx.compose.material3.ExperimentalMaterial3Api",
        "androidx.compose.material3.Text",
    )
    if (generateSwipeRefresh) {
        imports.addAll(
            listOf(
                "androidx.compose.runtime.LaunchedEffect",
                "androidx.compose.runtime.getValue",
                "androidx.compose.runtime.mutableStateOf",
                "androidx.compose.runtime.remember",
                "androidx.compose.runtime.setValue",
                "com.google.accompanist.swiperefresh.SwipeRefresh",
                "com.google.accompanist.swiperefresh.rememberSwipeRefreshState",
                "kotlinx.coroutines.delay",
            )
        )
    }
    return getComposeCommonImports(imports)
}

private fun getArgSymbol() = "$"
package com.example.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun <T> PaginatedLazyColumn(
        modifier: Modifier = Modifier,
        items: List<T>,
        isLastPage: Boolean,
        isLoading: Boolean = false,
        onLoadMore: () -> Unit,
        itemContent: @Composable (T) -> Unit,
        emptyContent: @Composable () -> Unit = {}
) {
    val lazyState = rememberLazyListState()

    // Trigger load more when near the end of the list
    val shouldLoadMore = remember(items, isLastPage) {
        derivedStateOf {
            !isLastPage && !isLoading && lazyState.layoutInfo.let {
                it.visibleItemsInfo.lastOrNull()?.index == it.totalItemsCount - 1
            }
        }
    }

    // Use LaunchedEffect to load more items when reaching the end
    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            onLoadMore()
        }
    }

    if (items.isNotEmpty()) {
        LazyColumn(
                state = lazyState,
                modifier = modifier
        ) {
            // Render list items
            items(items) { item ->
                itemContent(item)
            }
        }
    } else {
        emptyContent()
    }
}
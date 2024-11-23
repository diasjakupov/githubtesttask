package com.example.presentation.screens.reposcreen.remote

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.base.ActionCallback
import com.example.core.ui.components.NetworkComponent
import com.example.core.ui.components.PaginatedLazyColumn
import com.example.core.ui.theme.ZimranGithubTheme
import com.example.gitrepos.presentation.R
import com.example.presentation.components.BottomFiltering
import com.example.presentation.components.RepoSearchBar
import com.example.presentation.components.RepositoryItem
import com.example.presentation.screens.reposcreen.RepoActions
import com.example.presentation.uimodels.RepositoryUiModel
import kotlinx.coroutines.delay

@Composable
fun RepoScreen(
        state: RepoScreenState,
        callback: ActionCallback<RepoActions>
) {
    NetworkComponent(
            isLoading = state.isLoading,
            errorMessage = state.errorMessage,
            errorMessageString = state.errorMessageText,
            onCancel = {
                callback.sendAction(RepoActions.DismissErrorDialog)
            }) {
        LaunchedEffect(Unit) {
            if (state.searchQuery.isEmpty()) {
                callback.sendAction(RepoActions.LoadRepos(""))
                return@LaunchedEffect
            }
        }

        LaunchedEffect(Unit) {
            callback.sendAction(RepoActions.Synchronize)
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                RepoSearchBar(
                        query = state.searchQuery,
                        onSearch = { callback.sendAction(RepoActions.LoadRepos(state.searchQuery)) },
                        onQueryChange = { callback.sendAction(RepoActions.OnSearchChange(it)) },
                        onTrailingIconClick = { callback.sendAction(RepoActions.ToggleFilter) }
                )

                PaginatedLazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        items = state.data,
                        isLastPage = state.isLastPage,
                        onLoadMore = {
                            callback.sendAction(RepoActions.LoadMore)
                        },
                        itemContent = {
                            RepositoryItem(
                                    repository = it,
                                    onClick = { callback.sendAction(RepoActions.OpenRepo(it)) },
                                    onUsernameClick = { callback.sendAction(RepoActions.OpenAuthorsRepos(it)) }
                            )
                        },
                        emptyContent = {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(
                                            painter = painterResource(R.drawable.empty),
                                            contentDescription = null,
                                            modifier = Modifier.size(100.dp)
                                    )
                                    Spacer(Modifier.height(16.dp))
                                    Text(
                                            text = stringResource(
                                                    if (state.previousSearchQuery?.isEmpty() == true)
                                                        R.string.empty_search
                                                    else R.string.no_data_found
                                            ),
                                            style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                )
            }

            BottomFiltering(
                    isVisible = state.showFilters,
                    selectedFilter = state.selectedFilters,
                    onSelect = { callback.sendAction(RepoActions.SelectFilterSort(it)) },
                    onConfirm = { callback.sendAction(RepoActions.ApplyFilter) }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RepoScreenPreview() {
    ZimranGithubTheme {
        RepoScreen(state = RepoScreenState()) {}
    }
}
package com.example.presentation.screens.reposcreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.base.ActionCallback
import com.example.core.ui.theme.ZimranGithubTheme
import com.example.gitrepos.presentation.R
import com.example.presentation.components.BottomFiltering
import com.example.presentation.components.RepoSearchBar
import com.example.presentation.components.RepositoryItem
import com.example.presentation.screens.reposcreen.remote.RepoScreenState

@[OptIn(ExperimentalMaterial3Api::class) Composable]
fun DefaultRepoScreen(state: RepoScreenState,
                      topBar: String,
                      callback: ActionCallback<RepoActions>) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TopAppBar(title = { Text(text = topBar,
                                     style = MaterialTheme.typography.titleLarge) }, navigationIcon = {
                IconButton(onClick = {
                    callback.sendAction(RepoActions.NavigateToMain)
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                }
            }, windowInsets = WindowInsets(top = 0.dp))

            HorizontalDivider()

            Spacer(Modifier.height(24.dp))

            RepoSearchBar(query = state.searchQuery,
                          onSearch = { callback.sendAction(RepoActions.LoadRepos(state.searchQuery)) },
                          onQueryChange = { callback.sendAction(RepoActions.OnSearchChange(it)) },
                          onTrailingIconClick = { callback.sendAction(RepoActions.ToggleFilter) })

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.data) {
                    RepositoryItem(it) {
                        callback.sendAction(RepoActions.OpenRepo(it))
                    }
                }
            }
        }

        BottomFiltering(state.showFilters, state.selectedFilters, {
            callback.sendAction(RepoActions.SelectFilterSort(it))
        }) { callback.sendAction(RepoActions.ApplyFilter) }
    }
}

@[Preview Composable]
fun LocalRepoScreenPreview() {
    ZimranGithubTheme {
        DefaultRepoScreen(RepoScreenState(), stringResource(R.string.history)) {}
    }
}
package com.example.presentation.screens.users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.base.ActionCallback
import com.example.core.ui.components.NetworkComponent
import com.example.presentation.components.RepoSearchBar
import com.example.presentation.components.UserCard
import com.example.presentation.screens.reposcreen.RepoActions

@Composable
fun UserScreen(state: UserState, callback: ActionCallback<UserActions>) =
        NetworkComponent(isLoading = state.isLoading, errorMessage = state.errorMessage, onCancel = {callback.sendAction(UserActions.DismissErrorDialog)}) {

            LaunchedEffect(Unit) { callback.sendAction(UserActions.LoadUsers) }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                RepoSearchBar(
                        query = state.searchQuery,
                        onSearch = { callback.sendAction(UserActions.OnSearchSubmit(state.searchQuery)) },
                        onQueryChange = { callback.sendAction(UserActions.OnSearchChange(it)) },
                        onTrailingIconClick = null
                )


                LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.data) {
                        UserCard(user = it)
                    }
                }

            }

        }

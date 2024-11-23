package com.example.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.presentation.screens.reposcreen.RepoActions

@[OptIn(ExperimentalMaterial3Api::class) Composable]
fun RepoSearchBar(query: String,
                  onQueryChange: (String) -> Unit,
                  onSearch: (String) -> Unit,
                  onTrailingIconClick: (() -> Unit)? = null){
    DockedSearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                        query = query,
                        onQueryChange = onQueryChange,
                        onSearch = onSearch,
                        expanded = false,
                        onExpandedChange = { },
                        placeholder = { Text(stringResource(com.example.gitrepos.presentation.R.string.searchBarHint)) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            if(onTrailingIconClick != null){
                                IconButton(onTrailingIconClick) {
                                    Icon(Icons.Default.MoreVert, contentDescription = null)
                                }
                            }
                        },
                )
            },
            expanded = false,
            onExpandedChange = { },
    ) {

    }
}
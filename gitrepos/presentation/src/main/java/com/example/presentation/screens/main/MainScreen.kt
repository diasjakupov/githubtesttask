package com.example.presentation.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.ui.theme.ZimranGithubTheme
import com.example.gitrepos.presentation.R
import com.example.presentation.navigation.BottomNavBarItems
import com.example.presentation.navigation.NestedNavigation
import com.example.presentation.navigation.ReposDestinations

@[OptIn(ExperimentalMaterial3Api::class) Composable]
fun MainScreen(local: NavHostController, parent: NavHostController) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
            bottomBar = {
                NavigationBar {
                    BottomNavBarItems.entries.forEachIndexed { idx, entry ->
                        NavigationBarItem(
                                icon = { Icon(painter = painterResource(entry.iconId), null) },
                                label = { Text(stringResource(entry.titleId)) },
                                selected = idx == selectedIndex,
                                onClick = {
                                    selectedIndex = idx
                                    local.navigate(entry.getDestination())
                                }
                        )
                    }
                }
            },
            floatingActionButton = {
                if (BottomNavBarItems.entries[selectedIndex] == BottomNavBarItems.Home) {
                    IconButton(modifier = Modifier.size(48.dp), onClick = {
                        parent.navigate(ReposDestinations.HistoryScreen)
                    }, colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                        Icon(modifier = Modifier.size(32.dp), painter = painterResource(R.drawable.history), contentDescription = null)
                    }
                }

            }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NestedNavigation(local, parent)
        }
    }
}

@[Composable Preview]
fun ReposScreenPreview() {
    ZimranGithubTheme {
        MainScreen(rememberNavController(), rememberNavController())
    }
}

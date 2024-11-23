package com.example.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.gitrepos.presentation.R
import com.example.presentation.screens.main.MainScreen
import com.example.presentation.screens.reposcreen.RepoActions
import com.example.presentation.screens.reposcreen.RepoEvents
import com.example.presentation.screens.reposcreen.DefaultRepoScreen
import com.example.presentation.screens.reposcreen.local.LocalViewModel
import com.example.presentation.screens.reposcreen.remote.MainRepoViewModel
import com.example.presentation.screens.reposcreen.remote.RepoScreen
import com.example.presentation.screens.reposcreen.remote.RepoViewModel
import com.example.presentation.screens.reposcreen.remote.UsersRepoViewModel
import com.example.presentation.screens.users.UserScreen
import com.example.presentation.screens.users.UserViewModel
import com.example.presentation.screens.webview.RepoWebView

fun NavGraphBuilder.reposNavigation(navController: NavHostController) {
    navigation<ReposDestinations.Parent>(startDestination = ReposDestinations.Main) {
        composable<ReposDestinations.Main> {
            MainScreen(rememberNavController(), navController)
        }
        composable<ReposDestinations.RepoDetails> {
            val parentEntry = remember { navController.getBackStackEntry(ReposDestinations.Parent) }
            val reposViewModel = hiltViewModel<MainRepoViewModel>(parentEntry)

            val route: ReposDestinations.RepoDetails = it.toRoute()
            RepoWebView(route.url) {
                reposViewModel.handleActions(it)
            }
        }
        composable<ReposDestinations.HistoryScreen> {
            val localViewModel = hiltViewModel<LocalViewModel>()

            val state by localViewModel.state.collectAsState()
            val events by localViewModel.events.collectAsState(RepoEvents.None)

            LaunchedEffect(events) {
                if (events is RepoEvents.ToMain) navController.navigate(ReposDestinations.Main)
            }

            LaunchedEffect(Unit) {
                localViewModel.handleActions(RepoActions.LoadRepos(""))
            }

            DefaultRepoScreen(state, stringResource(R.string.history)) {
                localViewModel.handleActions(it)
            }
        }

        composable<ReposDestinations.UsersRepoSearchScreen> {
            val reposViewModel = hiltViewModel<UsersRepoViewModel>(it)
            val state by reposViewModel.state.collectAsState()
            val events by reposViewModel.events.collectAsState(RepoEvents.None)

            LaunchedEffect(events) {
                if (events is RepoEvents.OpenRepo) navController.navigate(ReposDestinations.RepoDetails((events as RepoEvents.OpenRepo).url))
            }

            RepoScreen(
                    state = state,
                    callback = {
                        reposViewModel.handleActions(it)
                    }
            )
        }

    }
}

@Composable
fun NestedNavigation(localNavController: NavHostController, parentNavController: NavHostController) {
    NavHost(navController = localNavController, startDestination = ReposDestinations.RepoSearchScreen) {
        composable<ReposDestinations.RepoSearchScreen> {
            val parentEntry = remember { parentNavController.getBackStackEntry(ReposDestinations.Parent) }
            val reposViewModel = hiltViewModel<MainRepoViewModel>(parentEntry)
            val state by reposViewModel.state.collectAsState()
            val events by reposViewModel.events.collectAsState(RepoEvents.None)

            LaunchedEffect(events) {
                when (events) {
                    is RepoEvents.OpenRepo -> parentNavController.navigate(ReposDestinations.RepoDetails((events as RepoEvents.OpenRepo).url))
                    is RepoEvents.OpenAuthorsRepos -> parentNavController.navigate(ReposDestinations.UsersRepoSearchScreen((events as RepoEvents.OpenAuthorsRepos).username))
                    else -> Unit
                }
            }

            RepoScreen(
                    state = state,
                    callback = {
                        reposViewModel.handleActions(it)
                    }
            )
        }
        composable<ReposDestinations.Users> {
            val userViewModel = hiltViewModel<UserViewModel>()
            val state by userViewModel.state.collectAsState()

            UserScreen(state = state, callback = {
                userViewModel.handleActions(it)
            })
        }
    }
}
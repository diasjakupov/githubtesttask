package com.example.presentation.navigation

import android.app.Activity
import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.presentation.screen.login.GitHubLoginScreen
import com.example.presentation.screen.viewModel.LoginViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.toRoute
import com.example.presentation.screen.viewModel.LoginActions
import com.example.presentation.screen.viewModel.LoginEvents


fun NavGraphBuilder.authNavigation(navController: NavController){
    composable<LoginDestinations.LoginScreen>{
        val route = it.toRoute<LoginDestinations.LoginScreen>().isRelogin
        val loginViewModel = hiltViewModel<LoginViewModel>()

        val state by loginViewModel.state.collectAsState()
        val events by loginViewModel.events.collectAsState(LoginEvents.None)

        LaunchedEffect(events) {
            when (events) {
                is LoginEvents.NavigateToMain -> navController.navigate(ReposDestinations.Main)
                else -> {}
            }
        }

        GitHubLoginScreen(state = state, callback = {
            loginViewModel.handleActions(it)
        })
    }
}
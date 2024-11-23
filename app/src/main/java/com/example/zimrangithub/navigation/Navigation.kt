package com.example.zimrangithub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.presentation.navigation.LoginDestinations
import com.example.presentation.navigation.authNavigation
import com.example.presentation.navigation.reposNavigation
import com.example.zimrangithub.HandleLoginBroadcast


@Composable
fun Navigation(){
    val navController = rememberNavController()

    HandleLoginBroadcast(context = LocalContext.current){
        navController.navigate(LoginDestinations.LoginScreen(isRelogin = true)){
            popUpTo(0)
        }
    }

    NavHost(navController = navController, startDestination = LoginDestinations.LoginScreen(isRelogin = false)){
        authNavigation(navController)
        reposNavigation(navController)
    }
}
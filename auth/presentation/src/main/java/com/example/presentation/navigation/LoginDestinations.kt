package com.example.presentation.navigation

import kotlinx.serialization.Serializable


sealed interface LoginDestinations {
    @Serializable
    data class LoginScreen(val isRelogin: Boolean = false): LoginDestinations
}
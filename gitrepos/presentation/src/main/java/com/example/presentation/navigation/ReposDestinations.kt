package com.example.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface ReposDestinations {
    @Serializable
    data object Parent: ReposDestinations

    @Serializable
    data object HistoryScreen: ReposDestinations

    @Serializable
    data class UsersRepoSearchScreen(val username: String?): ReposDestinations

    @Serializable
    data object Main: ReposDestinations

    @Serializable
    data object RepoSearchScreen: ReposDestinations

    @Serializable
    data object Users: ReposDestinations

    @Serializable
    data class RepoDetails(val url: String): ReposDestinations
}
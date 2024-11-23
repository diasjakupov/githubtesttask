package com.example.presentation.navigation


enum class BottomNavBarItems(
        val titleId: Int,
        val iconId: Int
) {
    Home(com.example.gitrepos.presentation.R.string.search, com.example.gitrepos.presentation.R.drawable.search),
    Users(com.example.gitrepos.presentation.R.string.users, com.example.gitrepos.presentation.R.drawable.person);


    fun getDestination(): ReposDestinations = when (this) {
        Home -> ReposDestinations.RepoSearchScreen
        Users -> ReposDestinations.Users
    }
}
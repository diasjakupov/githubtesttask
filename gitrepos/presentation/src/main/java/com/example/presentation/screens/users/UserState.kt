package com.example.presentation.screens.users

import com.example.presentation.uimodels.UserUiModel

data class UserState(
        val data: List<UserUiModel> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: Int? = null,
        val searchQuery: String = ""
)

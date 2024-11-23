package com.example.presentation.screens.users

import com.example.core.base.ViewModelActions
import com.example.core.base.ViewModelEvents
import com.example.presentation.screens.reposcreen.RepoActions

interface UserActions: ViewModelActions{
    data object LoadUsers: UserActions
    data object DismissErrorDialog: UserActions

    data class OnSearchSubmit(val search: String):UserActions
    data class OnSearchChange(val search: String): UserActions

}

interface UserEvents: ViewModelEvents
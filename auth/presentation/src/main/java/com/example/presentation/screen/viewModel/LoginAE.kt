package com.example.presentation.screen.viewModel

import android.app.Activity
import com.example.core.base.ViewModelActions
import com.example.core.base.ViewModelEvents

sealed interface LoginActions: ViewModelActions{
    data class OnLoginClick(val activity: Activity): LoginActions
    data object NavigateIfLoggedIn: LoginActions
}
sealed interface LoginEvents: ViewModelEvents{
    data object None: LoginEvents
    data object NavigateToMain: LoginEvents
}
package com.example.presentation.screen.viewModel

import android.app.Activity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.core.base.BaseViewModel
import com.example.core.base.BaseViewModelContract
import com.example.domain.repository.OAuthRepository
import com.example.presentation.navigation.LoginDestinations
import com.example.presentation.screen.login.LoginState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.OAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


interface LoginViewModelContract : BaseViewModelContract<LoginActions, LoginEvents> {

    val state: MutableStateFlow<LoginState>
}

@HiltViewModel
class LoginViewModel @Inject constructor(
        private val authRepository: OAuthRepository,
        private val provider: OAuthProvider,
        private val firebaseAuth: FirebaseAuth,
        private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<LoginActions, LoginEvents>(), LoginViewModelContract {

    override val state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())

    override fun handleActions(action: LoginActions) {
        when (action) {
            is LoginActions.OnLoginClick -> handleLogin(action.activity)
            is LoginActions.NavigateIfLoggedIn -> navigateIfLoggedIn()
        }
    }


    private fun navigateIfLoggedIn() = viewModelScope.launch{
        if(savedStateHandle.toRoute<LoginDestinations.LoginScreen>().isRelogin) return@launch
        val credentials = authRepository.getOAuthCredentials().firstOrNull()
        if(credentials != null && credentials.accessToken.isNotEmpty()) pushEvent(LoginEvents.NavigateToMain)
    }

    private fun handleLogin(activity: Activity) = viewModelScope.launch {
        val isLoggedIn = authRepository.login().getOrNull()
        val token = (firebaseAuth.startActivityForSignInWithProvider(activity, provider).await().credential as? OAuthCredential)?.accessToken
        if (isLoggedIn != true){
            authRepository.login(token.orEmpty()).onSuccess {
                pushEvent(LoginEvents.NavigateToMain)
            }.onFailure {
                state.update { it.copy(isError = true, errorMessage = com.example.auth.presentation.R.string.login_error) }
            }
        }else{
            pushEvent(LoginEvents.NavigateToMain)
        }
    }
}
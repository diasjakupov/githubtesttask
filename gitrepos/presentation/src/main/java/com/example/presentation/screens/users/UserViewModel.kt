package com.example.presentation.screens.users

import androidx.lifecycle.viewModelScope
import com.example.core.base.BaseViewModel
import com.example.core.base.BaseViewModelContract
import com.example.domain.entity.UserInfo
import com.example.domain.repository.UsersRepository
import com.example.presentation.screens.reposcreen.RepoActions
import com.example.presentation.uimodels.UserUiModel
import com.example.presentation.uimodels.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


interface UserViewModelContract : BaseViewModelContract<UserActions, UserEvents> {

    val state: StateFlow<UserState>
}

@HiltViewModel
class UserViewModel @Inject constructor(
        private val usersRepository: UsersRepository,
) : BaseViewModel<UserActions, UserEvents>(), UserViewModelContract {

    override val state = MutableStateFlow(UserState())

    override fun handleActions(action: UserActions) {
        when(action){
            is UserActions.LoadUsers -> loadUsers()
            is UserActions.DismissErrorDialog -> state.update { it.copy(errorMessage = null) }
            is UserActions.OnSearchChange -> state.update { it.copy(searchQuery = action.search) }
            is UserActions.OnSearchSubmit -> searchUsers(action.search)
        }
    }


    private fun searchUsers(search: String) = viewModelScope.launch {
        state.update { it.copy(isLoading = true) }
        usersRepository.getLocalUserInfos(search).onSuccess { res ->
            state.update { it.copy(data = res.map(UserInfo::toUIModel), isLoading = false) }
        }.onFailure {
            state.update { it.copy(isLoading = false) }
        }
    }

    private fun loadUsers() = viewModelScope.launch {
        state.update { it.copy(isLoading = true) }
        usersRepository.getLocalUserInfos().onSuccess { res ->
            state.update { it.copy(data = res.map(UserInfo::toUIModel), isLoading = false) }
        }.onFailure {
            state.update { it.copy(isLoading = false) }
        }
    }
}
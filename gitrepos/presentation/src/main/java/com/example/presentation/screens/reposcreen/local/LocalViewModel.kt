package com.example.presentation.screens.reposcreen.local

import androidx.lifecycle.viewModelScope
import com.example.core.base.BaseViewModel
import com.example.core.base.BaseViewModelContract
import com.example.domain.entity.GitRepos
import com.example.domain.repository.ReposRepository
import com.example.presentation.screens.reposcreen.RepoActions
import com.example.presentation.screens.reposcreen.RepoEvents
import com.example.presentation.screens.reposcreen.RepoFilteringContract
import com.example.presentation.screens.reposcreen.remote.RepoScreenState
import com.example.presentation.uimodels.RepositoryUiModel
import com.example.presentation.uimodels.sortBy
import com.example.presentation.uimodels.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


interface LocalViewModelContract : BaseViewModelContract<RepoActions, RepoEvents>, RepoFilteringContract {

    val state: StateFlow<RepoScreenState>
}


@HiltViewModel
class LocalViewModel @Inject constructor(
        private val repository: ReposRepository
): BaseViewModel<RepoActions, RepoEvents>(), LocalViewModelContract{


    override val state = MutableStateFlow(RepoScreenState())
    override val unfilteredData = mutableListOf<RepositoryUiModel>()


    override fun handleActions(action: RepoActions) {
        when(action){
            is RepoActions.LoadRepos -> initialLoading(action.search)
            is RepoActions.OnSearchChange -> state.update { it.copy(searchQuery = action.search) }
            is RepoActions.SelectFilterSort -> state.update { it.copy(selectedFilters = action.filter) }
            is RepoActions.ApplyFilter -> applyFilters()
            is RepoActions.ToggleFilter -> state.update { it.copy(showFilters = !it.showFilters) }
            is RepoActions.NavigateToMain -> viewModelScope.launch { pushEvent(RepoEvents.ToMain) }
            else -> Unit
        }
    }


    private fun initialLoading(search: String) = viewModelScope.launch {
        val data = repository.getViewedRepos(search).getOrNull()?.map(GitRepos::toUIModel)
        data?.refreshUnfiltered()

        state.emit(state.value.copy(previousSearchQuery = search, data = data.orEmpty()))
    }

    private fun applyFilters() = viewModelScope.launch {
        state.update { it.copy(data = unfilteredData.sortBy(it.selectedFilters), showFilters = false) }
    }

}
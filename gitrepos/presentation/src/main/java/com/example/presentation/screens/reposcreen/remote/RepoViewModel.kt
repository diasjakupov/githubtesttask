package com.example.presentation.screens.reposcreen.remote


import androidx.compose.ui.util.fastDistinctBy
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.core.base.BaseViewModel
import com.example.core.base.BaseViewModelContract
import com.example.domain.entity.GitRepos
import com.example.domain.repository.ReposRepository
import com.example.domain.repository.UsersRepository
import com.example.gitrepos.presentation.R
import com.example.presentation.navigation.ReposDestinations
import com.example.presentation.screens.reposcreen.RepoActions
import com.example.presentation.screens.reposcreen.RepoEvents
import com.example.presentation.screens.reposcreen.RepoFilteringContract
import com.example.presentation.uimodels.RepositoryUiModel
import com.example.presentation.uimodels.combineRepositoryLists
import com.example.presentation.uimodels.search
import com.example.presentation.uimodels.sortBy
import com.example.presentation.uimodels.toDomain
import com.example.presentation.uimodels.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


interface RepoViewModelContract : BaseViewModelContract<RepoActions, RepoEvents>, RepoFilteringContract {

    val state: StateFlow<RepoScreenState>
}

@HiltViewModel
class UsersRepoViewModel @Inject constructor(
        private val repository: ReposRepository,
        private val usersRepository: UsersRepository,
        savedStateHandle: SavedStateHandle
) : RepoViewModel(repository, usersRepository) {

    private val argUsername = savedStateHandle.toRoute<ReposDestinations.UsersRepoSearchScreen>().username.orEmpty()

    override suspend fun getRepos(query: String, page: Int): Result<List<GitRepos>> {
        return if (argUsername.isEmpty()) repository.getReposRemote(query, page)
        else if ((argUsername.isNotEmpty() && (state.value.page != page || state.value.page == null)) || query.isEmpty()) repository.getUserReposRemote(
                argUsername,
                page
        )
        else Result.success(state.value.data.search(query).sortBy(state.value.selectedFilters).map(RepositoryUiModel::toDomain))
    }

    public override suspend fun loadLocalRepos(query: String): Result<List<GitRepos>> = repository.getViewedRepos(query, argUsername)
}

@HiltViewModel
class MainRepoViewModel @Inject constructor(
        private val repository: ReposRepository,
        private val usersRepository: UsersRepository,
        ) : RepoViewModel(repository, usersRepository) {

    override suspend fun loadRepos(query: String, page: Int): List<RepositoryUiModel> {
        if (query.isEmpty()) return emptyList()
        return super.loadRepos(query, page)
    }

    override suspend fun getRepos(query: String, page: Int): Result<List<GitRepos>> {
        return if (query.isNotEmpty()) repository.getReposRemote(query, page) else Result.success(emptyList())
    }

    override suspend fun loadLocalRepos(query: String): Result<List<GitRepos>> = repository.getViewedRepos(query)
}

abstract class RepoViewModel(
        private val repository: ReposRepository,
        private val usersRepository: UsersRepository,
        ) : BaseViewModel<RepoActions, RepoEvents>(), RepoViewModelContract {

    override val state: MutableStateFlow<RepoScreenState> = MutableStateFlow(RepoScreenState())
    override val unfilteredData = mutableListOf<RepositoryUiModel>()

    override fun handleActions(action: RepoActions) {
        when (action) {
            is RepoActions.Synchronize -> synchronizeRepos()
            is RepoActions.LoadRepos -> loadData(action.search)
            is RepoActions.OnSearchChange -> state.update { it.copy(searchQuery = action.search) }
            is RepoActions.DismissErrorDialog -> state.update { it.copy(errorMessage = null, errorMessageText = null) }
            is RepoActions.SelectFilterSort -> state.update { it.copy(selectedFilters = action.filter) }
            is RepoActions.ApplyFilter -> applyFilters()
            is RepoActions.ToggleFilter -> state.update { it.copy(showFilters = !it.showFilters) }
            is RepoActions.MarkAsViewed -> markAsViewed(action.url)
            is RepoActions.OpenRepo -> openRepo(action.repositoryUiModel.url)
            is RepoActions.OpenAuthorsRepos -> openAuthorsRepos(action.repositoryUiModel.author)
            is RepoActions.LoadMore -> loadMoreItems()
            else -> Unit
        }
    }

    private fun loadMoreItems() = viewModelScope.launch {
        if (state.value.isLastPage || state.value.data.size < PAGE_SIZE) return@launch
        state.emit(state.value.copy(isLoading = true))
        val newRepos = loadRepos(state.value.searchQuery, state.value.page?.inc() ?: 1)

        val isLastPage = newRepos.size < PAGE_SIZE

        val updatedDataSet = (unfilteredData + newRepos).fastDistinctBy { it.url }
        updatedDataSet.refreshUnfiltered()


        state.update {
            it.copy(
                    data = updatedDataSet.sortBy(it.selectedFilters),
                    page = it.page?.inc(),
                    isLastPage = isLastPage,
                    isLoading = false
            )
        }
    }

    private fun markAsViewed(url: String) = viewModelScope.launch {
        unfilteredData.find { it.url == url }?.toDomain()?.let { repository.markAsViewed(it) }
    }

    private fun applyFilters() = viewModelScope.launch {
        state.update { it.copy(data = unfilteredData.sortBy(it.selectedFilters), showFilters = false) }
    }

    private fun openRepo(url: String?) = viewModelScope.launch {
        url?.let {
            pushEvent(RepoEvents.OpenRepo(it))
        } ?: state.update { it.copy(errorMessage = R.string.url_load_error) }
    }

    private fun openAuthorsRepos(author: String?) = viewModelScope.launch {
        author?.let {
            usersRepository.getUserInfo(it).onSuccess {
                usersRepository.markUserAsViewed(it)
                pushEvent(RepoEvents.OpenAuthorsRepos(author))
            }
        } ?: state.update { it.copy(errorMessage = R.string.url_load_error) }
    }

    protected fun loadData(query: String) = viewModelScope.launch {
        unfilteredData.clear()

        state.update { it.copy(previousSearchQuery = query, isLoading = true) }

        val newRepos = loadRepos(query, 1)

        state.update {
            it.copy(
                    data = newRepos.sortBy(it.selectedFilters),
                    isLoading = false,
                    page = 1
            )
        }
    }

    private fun synchronizeRepos() = viewModelScope.launch {
        if (state.value.previousSearchQuery.isNullOrEmpty()) return@launch

        val localRequest = combineRepositoryLists(
                repository.getViewedRepos().getOrNull()?.map(GitRepos::toUIModel).orEmpty(),
                unfilteredData
        )

        localRequest.refreshUnfiltered()

        state.update { it.copy(data = unfilteredData.sortBy(it.selectedFilters)) }
    }

    protected abstract suspend fun loadLocalRepos(query: String): Result<List<GitRepos>>

    protected open suspend fun loadRepos(query: String, page: Int): List<RepositoryUiModel> {
        val localRequest = loadLocalRepos(query).getOrNull()
        val remoteRequest = getRepos(query, page)

        remoteRequest.onFailure { error -> state.update { it.copy(errorMessageText = error.message) } }

        val combinedData = unfilteredData + combineRepositoryLists(
                localRequest?.map(GitRepos::toUIModel).orEmpty(),
                remoteRequest.getOrNull()?.map(GitRepos::toUIModel).orEmpty()
        ).fastDistinctBy { it.url }

        combinedData.refreshUnfiltered()

        return combinedData
    }

    abstract suspend fun getRepos(query: String, page: Int): Result<List<GitRepos>>

    companion object {

        private const val PAGE_SIZE = 30
    }
}










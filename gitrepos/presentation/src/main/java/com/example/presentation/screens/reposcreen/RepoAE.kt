package com.example.presentation.screens.reposcreen

import com.example.core.base.ViewModelActions
import com.example.core.base.ViewModelEvents
import com.example.presentation.screens.reposcreen.remote.SortBy
import com.example.presentation.uimodels.RepositoryUiModel

sealed interface RepoActions: ViewModelActions{
    data class LoadRepos(val search: String):RepoActions
    data class OnSearchChange(val search: String): RepoActions
    data class SelectFilterSort(val filter: SortBy): RepoActions
    data class MarkAsViewed(val url: String): RepoActions
    data class OpenRepo(val repositoryUiModel: RepositoryUiModel): RepoActions
    data class OpenAuthorsRepos(val repositoryUiModel: RepositoryUiModel): RepoActions


    data object NavigateToMain: RepoActions
    data object Synchronize: RepoActions
    data object ApplyFilter: RepoActions
    data object DismissErrorDialog: RepoActions
    data object ToggleFilter: RepoActions
    data object LoadMore: RepoActions
}



sealed interface RepoEvents: ViewModelEvents{
    data object None: RepoEvents

    data object ToMain: RepoEvents

    data class OpenRepo(val url: String): RepoEvents
    data class OpenAuthorsRepos(val username: String): RepoEvents
}
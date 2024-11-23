package com.example.presentation.screens.reposcreen.remote

// State and enum classes
import androidx.annotation.StringRes
import com.example.gitrepos.presentation.R
import com.example.presentation.uimodels.RepositoryUiModel

data class RepoScreenState(
        val data: List<RepositoryUiModel> = emptyList(),
        val page: Int? = null,
        val isLastPage: Boolean = false,
        val searchQuery: String = "",
        val previousSearchQuery: String? = null,
        val selectedFilters: SortBy = SortBy.UPDATED_DATE,
        val showFilters: Boolean = false,
        val isLoading: Boolean = false,
        val errorMessage: Int? = null,
        val errorMessageText: String? = null,
)

enum class SortBy(@StringRes val nameId: Int) {
    UPDATED_DATE(R.string.updated_date), STARS(R.string.stars_label), FORKS(R.string.forks_label)
}
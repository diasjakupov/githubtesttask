package com.example.presentation.uimodels

import com.example.domain.entity.GitRepos
import com.example.presentation.screens.reposcreen.remote.SortBy
import kotlinx.datetime.LocalDateTime

data class RepositoryUiModel(
        val id: String?,
        val author: String?,
        val authorUrl: String?,
        val title: String?,
        val description: String?,
        val stars: Int?,
        val forks: Int?,
        val isLocallySaved: Boolean = false,
        val url: String?,
        val datetime: LocalDateTime?
)

fun List<RepositoryUiModel>.sortBy(sortBy: SortBy): List<RepositoryUiModel> =
        when (sortBy) {
            SortBy.UPDATED_DATE -> this.sortedByDescending { it.datetime }
            SortBy.FORKS -> this.sortedByDescending { it.forks }
            SortBy.STARS -> this.sortedByDescending { it.stars }
        }

fun List<RepositoryUiModel>.search(query: String): List<RepositoryUiModel> =
        this.filter { it.title?.contains(query) == true || it.description?.contains(query) == true }

fun combineRepositoryLists(local: List<RepositoryUiModel>, remote: List<RepositoryUiModel>): List<RepositoryUiModel> {
    val combinedMap = mutableMapOf<String, RepositoryUiModel>()
    local.forEach {
        if (it.url != null) {
            combinedMap[it.url] = it.copy(isLocallySaved = true)
        }
    }

    remote.forEach {
        if (it.url != null) {
            val existing = combinedMap[it.url]
            if (existing != null) {
                combinedMap[it.url] = existing.copy(isLocallySaved = true)
            } else {
                combinedMap[it.url] = it
            }
        }
    }

    return combinedMap.values.toList()
}


fun GitRepos.toUIModel() = RepositoryUiModel(
        id = id.toString(),
        author = ownerName,
        authorUrl = ownerUrl,
        title = name,
        description = description,
        stars = stars,
        forks = forks,
        url = url,
        datetime = datetime
)

fun RepositoryUiModel.toDomain() = GitRepos(
        id = id?.toIntOrNull() ?: -1,
        ownerName = author,
        ownerUrl = authorUrl,
        name = title,
        description = description,
        stars = stars,
        forks = forks,
        url = url,
        datetime = datetime,
        ownerId = null,
        fullName = null,
        language = null,
        ownerFollowersUrl = ""
)

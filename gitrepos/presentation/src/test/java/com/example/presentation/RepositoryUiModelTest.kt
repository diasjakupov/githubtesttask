package com.example.presentation

import com.example.domain.entity.GitRepos
import com.example.presentation.uimodels.*
import com.example.presentation.screens.reposcreen.remote.SortBy
import kotlinx.datetime.LocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Test

class RepositoryUiModelTest {

    @Test
    fun testSortByUpdatedDate() {
        val items = listOf(
                RepositoryUiModel(
                        id = "1",
                        author = "Author1",
                        authorUrl = "http://author1.com",
                        title = "Repo1",
                        description = "Description1",
                        stars = 10,
                        forks = 5,
                        isLocallySaved = false,
                        url = "http://repo1.com",
                        datetime = LocalDateTime(2023, 1, 1, 12, 0, 0)
                ),
                RepositoryUiModel(
                        id = "2",
                        author = "Author2",
                        authorUrl = "http://author2.com",
                        title = "Repo2",
                        description = "Description2",
                        stars = 20,
                        forks = 10,
                        isLocallySaved = false,
                        url = "http://repo2.com",
                        datetime = LocalDateTime(2023, 1, 2, 12, 0, 0)
                )
        )
        val sortedItems = items.sortBy(SortBy.UPDATED_DATE)
        assertEquals("2", sortedItems.first().id)
    }

    @Test
    fun testSortByForks() {
        val items = listOf(
                RepositoryUiModel(
                        id = "1",
                        author = "Author1",
                        authorUrl = "http://author1.com",
                        title = "Repo1",
                        description = "Description1",
                        stars = 10,
                        forks = 5,
                        isLocallySaved = false,
                        url = "http://repo1.com",
                        datetime = LocalDateTime(2023, 1, 1, 12, 0, 0)
                ),
                RepositoryUiModel(
                        id = "2",
                        author = "Author2",
                        authorUrl = "http://author2.com",
                        title = "Repo2",
                        description = "Description2",
                        stars = 20,
                        forks = 15,
                        isLocallySaved = false,
                        url = "http://repo2.com",
                        datetime = LocalDateTime(2023, 1, 2, 12, 0, 0)
                )
        )
        val sortedItems = items.sortBy(SortBy.FORKS)
        assertEquals("2", sortedItems.first().id)
    }

    @Test
    fun testSearchFiltersByTitleAndDescription() {
        val items = listOf(
                RepositoryUiModel(
                        id = "1",
                        author = "Author1",
                        authorUrl = "http://author1.com",
                        title = "Kotlin",
                        description = "Language",
                        stars = 100,
                        forks = 10,
                        isLocallySaved = false,
                        url = "http://repo1.com",
                        datetime = LocalDateTime(2023, 1, 1, 12, 0, 0)
                ),
                RepositoryUiModel(
                        id = "2",
                        author = "Author2",
                        authorUrl = "http://author2.com",
                        title = "Java",
                        description = "JVM Language",
                        stars = 50,
                        forks = 5,
                        isLocallySaved = false,
                        url = "http://repo2.com",
                        datetime = LocalDateTime(2023, 1, 2, 12, 0, 0)
                )
        )
        val results = items.search("Kotlin")
        assertEquals(1, results.size)
        assertEquals("1", results.first().id)
    }

    @Test
    fun testCombineRepositoryLists() {
        val local = listOf(
                RepositoryUiModel(
                        id = "1",
                        author = "Author1",
                        authorUrl = "http://author1.com",
                        title = "Repo1",
                        description = "Description1",
                        stars = 10,
                        forks = 5,
                        isLocallySaved = false,
                        url = "http://repo1.com",
                        datetime = LocalDateTime(2023, 1, 1, 12, 0, 0)
                )
        )
        val remote = listOf(
                RepositoryUiModel(
                        id = "1",
                        author = "Author1",
                        authorUrl = "http://author1.com",
                        title = "Repo1",
                        description = "Description1",
                        stars = 10,
                        forks = 5,
                        isLocallySaved = false,
                        url = "http://repo1.com",
                        datetime = LocalDateTime(2023, 1, 1, 12, 0, 0)
                ),
                RepositoryUiModel(
                        id = "2",
                        author = "Author2",
                        authorUrl = "http://author2.com",
                        title = "Repo2",
                        description = "Description2",
                        stars = 20,
                        forks = 10,
                        isLocallySaved = false,
                        url = "http://repo2.com",
                        datetime = LocalDateTime(2023, 1, 2, 12, 0, 0)
                )
        )
        val combined = combineRepositoryLists(local, remote)

        assertEquals(2, combined.size)
        assertEquals(true, combined.first { it.url == "http://repo1.com" }.isLocallySaved)
    }

    @Test
    fun testGitReposToUIModel() {
        val gitRepo = GitRepos(
                id = 1,
                ownerName = "Author",
                ownerUrl = "http://author.com",
                name = "RepoName",
                description = "A test repo",
                stars = 100,
                forks = 10,
                url = "http://repo.com",
                datetime = LocalDateTime(2023, 1, 1, 12, 0, 0),
                ownerId = null,
                fullName = null,
                language = null,
                ownerFollowersUrl = ""
        )
        val uiModel = gitRepo.toUIModel()

        assertEquals("1", uiModel.id)
        assertEquals("Author", uiModel.author)
        assertEquals("http://author.com", uiModel.authorUrl)
        assertEquals("RepoName", uiModel.title)
        assertEquals("A test repo", uiModel.description)
        assertEquals(100, uiModel.stars)
        assertEquals(10, uiModel.forks)
        assertEquals("http://repo.com", uiModel.url)
        assertEquals(LocalDateTime(2023, 1, 1, 12, 0, 0), uiModel.datetime)
    }
}


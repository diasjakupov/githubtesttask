package com.example.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.dao.GitRepoDao
import com.example.data.db.GitReposDB
import com.example.data.dto.GitRepoDTO
import com.example.data.dto.Owner
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class GitRepoDaoTest {

    private lateinit var database: GitReposDB
    private lateinit var gitRepoDao: GitRepoDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                GitReposDB::class.java
        ).allowMainThreadQueries().build()
        gitRepoDao = database.gitReposDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndRetrieveAllRepos() = runBlocking {
        val repo = GitRepoDTO(
                id = 1,
                name = "Repo1",
                description = "Test Repo",
                fullName = "User/Repo1",
                owner = Owner("login", 1, "url", "followers_url"),
                stars = 10,
                forks = 2,
                language = "Kotlin",
                url = "http://repo1.com",
                date = "2023-01-01"
        )
        gitRepoDao.insertRepo(repo)

        val repos = gitRepoDao.getAllRepos()
        assertEquals(1, repos.size)
        assertEquals(repo.name, repos[0].name)
        assertEquals(repo.owner?.login, repos[0].owner?.login)
    }

    @Test
    fun SearchReposByQuery() = runBlocking {
        val repo1 = GitRepoDTO(
                id = 1,
                name = "KotlinRepo",
                description = "Repo about Kotlin",
                fullName = "User/KotlinRepo",
                owner = Owner("user1", 1, "url", "followers_url"),
                stars = 50,
                forks = 5,
                language = "Kotlin",
                url = "http://kotlinrepo.com",
                date = "2023-01-01"
        )
        val repo2 = GitRepoDTO(
                id = 2,
                name = "JavaRepo",
                description = "Repo about Java",
                fullName = "User/JavaRepo",
                owner = Owner("user2", 2, "url", "followers_url"),
                stars = 60,
                forks = 6,
                language = "Java",
                url = "http://javarepo.com",
                date = "2023-01-02"
        )
        gitRepoDao.insertRepo(repo1)
        gitRepoDao.insertRepo(repo2)

        val results = gitRepoDao.getReposSearch("Kotlin")
        assertEquals(1, results.size)
        assertEquals(repo1.name, results[0].name)

        val userSpecific = gitRepoDao.getReposSearch("Java", "user2")
        assertEquals(1, userSpecific.size)
        assertEquals(repo2.name, userSpecific[0].name)
    }

    @Test
    fun InsertRepoWithLimitRemovesOldestWhenFull() = runBlocking {
        for (i in 1..GitRepoDao.TABLE_MAX_LIMIT + 1) {
            gitRepoDao.insertRepoWithLimit(
                    GitRepoDTO(
                            id = i,
                            name = "Repo$i",
                            description = "Description$i",
                            fullName = "User/Repo$i",
                            owner = Owner("user$i", i, "url$i", "followers_url$i"),
                            stars = i * 10,
                            forks = i,
                            language = "Kotlin",
                            url = "http://repo$i.com",
                            date = "2023-01-01"
                    )
            )
        }

        val repos = gitRepoDao.getAllRepos()
        assertEquals(GitRepoDao.TABLE_MAX_LIMIT, repos.size)
        assertTrue(repos.none { it.id == 1 })
    }

}

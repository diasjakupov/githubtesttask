package com.example.data

import com.example.data.api.ReposAPI
import com.example.data.dto.GitRepoDTO
import com.example.data.dto.GitRepoResponse
import com.example.data.dto.UserDTO
import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReposAPITest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: ReposAPI

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        api = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ReposAPI::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetRepos() = runBlocking {
        // Arrange
        val mockResponse = GitRepoResponse(
                totalCount = 1,
                items = listOf(
                        GitRepoDTO(
                                id = 1,
                                name = "TestRepo",
                                fullName = "User/TestRepo",
                                owner = null,
                                description = "A test repository",
                                stars = 100,
                                forks = 10,
                                language = "Kotlin",
                                url = "http://github.com/User/TestRepo",
                                date = "2023-01-01"
                        )
                )
        )
        val responseBody = Gson().toJson(mockResponse)
        mockWebServer.enqueue(MockResponse().setBody(responseBody).setResponseCode(200))

        // Act
        val result = api.getRepos(query = "Kotlin", page = 1)

        // Assert
        assertEquals(1, result.totalCount)
        assertEquals("TestRepo", result.items[0].name)
    }

    @Test
    fun testGetUsersRepos() = runBlocking {
        // Arrange
        val mockRepos = listOf(
                GitRepoDTO(
                        id = 1,
                        name = "Repo1",
                        fullName = "User/Repo1",
                        owner = null,
                        description = "Repository 1",
                        stars = 50,
                        forks = 5,
                        language = "Java",
                        url = "http://github.com/User/Repo1",
                        date = "2023-01-01"
                )
        )
        val responseBody = Gson().toJson(mockRepos)
        mockWebServer.enqueue(MockResponse().setBody(responseBody).setResponseCode(200))

        // Act
        val result = api.getUsersRepos(username = "User", page = 1)

        // Assert
        assertEquals(1, result.size)
        assertEquals("Repo1", result[0].name)
    }

    @Test
    fun testGetUserInfo() = runBlocking {
        // Arrange
        val mockUser = UserDTO(
                name = "User",
                followers = 100,
                bio = "A test user",
                image = "http://avatar.com/user.png",
                publicRepos = 10
        )
        val responseBody = Gson().toJson(mockUser)
        mockWebServer.enqueue(MockResponse().setBody(responseBody).setResponseCode(200))

        // Act
        val result = api.getUserInfo(username = "User")

        // Assert
        assertEquals("User", result.name)
        assertEquals(100, result.followers)
    }
}

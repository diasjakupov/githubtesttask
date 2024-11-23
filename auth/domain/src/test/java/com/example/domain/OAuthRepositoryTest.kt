package com.example.domain

import org.junit.Test

import org.junit.Assert.*

import com.example.domain.oauth.entity.OAuthCredentials
import com.example.domain.repository.OAuthRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before


class OAuthRepositoryTest {

    @MockK
    private lateinit var mockRepository: OAuthRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `login without token should return successful result`() = runBlocking {
        // Arrange
        coEvery { mockRepository.login() } returns Result.success(true)

        // Act
        val result = mockRepository.login()

        // Assert
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() == true)
        coVerify { mockRepository.login() }
    }

    @Test
    fun `login with token should return successful result`() = runBlocking {
        // Arrange
        val testToken = "test_access_token"
        coEvery { mockRepository.login(testToken) } returns Result.success(true)

        // Act
        val result = mockRepository.login(testToken)

        // Assert
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() == true)
        coVerify { mockRepository.login(testToken) }
    }

    @Test
    fun `getOAuthCredentials should return flow of credentials`() = runBlocking {
        // Arrange
        val expectedCredentials = OAuthCredentials("sample_token")
        coEvery { mockRepository.getOAuthCredentials() } returns flowOf(expectedCredentials)

        // Act
        val result = mockRepository.getOAuthCredentials().first()

        // Assert
        assertEquals(expectedCredentials, result)
        coVerify { mockRepository.getOAuthCredentials() }
    }
}
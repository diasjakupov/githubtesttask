package com.example.presentation

import com.example.domain.entity.UserInfo
import com.example.presentation.uimodels.UserUiModel
import com.example.presentation.uimodels.toUIModel
import org.junit.Assert.assertEquals
import org.junit.Test

class UserUiModelTest {

    @Test
    fun `test UserInfo to UserUiModel mapping`() {
        // Arrange
        val userInfo = UserInfo(
                name = "John Doe",
                bio = "Software Developer",
                followers = 1500,
                image = "http://example.com/johndoe.jpg",
                publicRepos = 10
        )

        // Act
        val uiModel = userInfo.toUIModel()

        // Assert
        assertEquals("http://example.com/johndoe.jpg", uiModel.profilePictureUrl)
        assertEquals("John Doe", uiModel.name)
        assertEquals("Software Developer", uiModel.bio)
        assertEquals(1500, uiModel.followers)
    }

    @Test
    fun `test UserInfo with empty fields maps to UserUiModel`() {
        // Arrange
        val userInfo = UserInfo(
                name = "",
                bio = "",
                followers = 0,
                image = "",
                publicRepos = 0
        )

        // Act
        val uiModel = userInfo.toUIModel()

        // Assert
        assertEquals("", uiModel.profilePictureUrl)
        assertEquals("", uiModel.name)
        assertEquals("", uiModel.bio)
        assertEquals(0, uiModel.followers)
    }
}

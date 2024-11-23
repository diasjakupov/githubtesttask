package com.example.data

import com.example.data.dto.UserDTO
import com.example.data.dto.toDomain
import com.example.data.dto.toDTO
import com.example.domain.entity.UserInfo
import org.junit.Assert.assertEquals
import org.junit.Test

class UserMappingTest {

    @Test
    fun `UserDTO toDomain maps correctly`() {
        val dto = UserDTO(
                name = "Test User",
                followers = 100,
                bio = "Test Bio",
                image = "http://avatar.com",
                publicRepos = 10
        )

        val domain = dto.toDomain()

        assertEquals(dto.name, domain.name)
        assertEquals(dto.followers ?: 0, domain.followers)
        assertEquals(dto.bio.orEmpty(), domain.bio)
        assertEquals(dto.image.orEmpty(), domain.image)
        assertEquals(dto.publicRepos ?: 0, domain.publicRepos)
    }

    @Test
    fun `UserInfo toDTO maps correctly`() {
        val domain = UserInfo(
                name = "Test User",
                followers = 100,
                bio = "Test Bio",
                image = "http://avatar.com",
                publicRepos = 10
        )

        val dto = domain.toDTO()

        assertEquals(domain.name, dto.name)
        assertEquals(domain.followers, dto.followers)
        assertEquals(domain.bio, dto.bio)
        assertEquals(domain.image, dto.image)
        assertEquals(domain.publicRepos, dto.publicRepos)
    }
}

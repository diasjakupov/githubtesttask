package com.example.data

import com.example.data.dto.GitRepoDTO
import com.example.data.dto.Owner
import com.example.data.dto.toDomain
import com.example.data.dto.toDTO
import com.example.domain.entity.GitRepos
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Test

class GitRepoMappingTest {

    @Test
    fun `GitRepoDTO toDomain maps correctly`() {
        val dto = GitRepoDTO(
                id = 1,
                name = "TestRepo",
                fullName = "Owner/TestRepo",
                owner = Owner(login = "Owner", id = 100, url = "http://owner.com", followerUrl = "http://followers.com"),
                description = "Test Description",
                stars = 50,
                forks = 10,
                language = "Kotlin",
                url = "http://repo.com",
                date = "2023-01-01T12:00:00Z"
        )

        val domain = dto.toDomain()

        assertEquals(dto.id, domain.id)
        assertEquals(dto.name, domain.name)
        assertEquals(dto.fullName, domain.fullName)
        assertEquals(dto.owner?.login, domain.ownerName)
        assertEquals(dto.owner?.followerUrl, domain.ownerFollowersUrl)
        assertEquals(dto.description, domain.description)
        assertEquals(dto.stars, domain.stars)
        assertEquals(dto.language, domain.language)
        assertEquals(dto.url, domain.url)
        assertEquals(dto.date?.let { Instant.parse(it).toLocalDateTime(TimeZone.UTC) }, domain.datetime)
    }

    @Test
    fun `GitRepos toDTO maps correctly`() {
        val domain = GitRepos(
                id = 1,
                name = "TestRepo",
                fullName = "Owner/TestRepo",
                ownerName = "Owner",
                ownerFollowersUrl = "http://followers.com",
                description = "Test Description",
                stars = 50,
                forks = 10,
                language = "Kotlin",
                url = "http://repo.com",
                ownerId = 100,
                ownerUrl = "http://owner.com",
                datetime = LocalDateTime(2023, 1, 1, 12, 0, 0)
        )

        val dto = domain.toDTO()

        assertEquals(domain.id, dto.id)
        assertEquals(domain.name, dto.name)
        assertEquals(domain.fullName, dto.fullName)
        assertEquals(domain.ownerName, dto.owner?.login)
        assertEquals(domain.ownerFollowersUrl, dto.owner?.followerUrl)
        assertEquals(domain.description, dto.description)
        assertEquals(domain.stars, dto.stars)
        assertEquals(domain.language, dto.language)
        assertEquals(domain.url, dto.url)
        assertEquals(domain.datetime?.toInstant(TimeZone.UTC).toString(), dto.date)
    }
}

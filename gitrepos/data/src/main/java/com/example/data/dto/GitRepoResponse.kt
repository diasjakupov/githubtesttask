package com.example.data.dto

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.db.OwnerTypeConverter
import com.example.domain.entity.GitRepos
import com.google.gson.annotations.SerializedName
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

data class GitRepoResponse(
        @SerializedName("total_count") val totalCount: Int,
        @SerializedName("items") val items: List<GitRepoDTO>
)

@Entity(
        tableName = "git_repo",
        indices = [Index(value = ["url"], unique = true)]
)
@TypeConverters(OwnerTypeConverter::class)
data class GitRepoDTO(
        @SerializedName("id") val id: Int?,
        @SerializedName("name") val name: String?,
        @SerializedName("full_name") val fullName: String?,
        @SerializedName("owner") val owner: Owner?,
        @SerializedName("description") val description: String?,
        @SerializedName("stargazers_count") val stars: Int?,
        @SerializedName("forks_count") val forks: Int?,
        @SerializedName("language") val language: String?,
        @SerializedName("html_url") val url: String?,
        @SerializedName("updated_at") val date: String?
) {

    @PrimaryKey(autoGenerate = true) var primeId: Int = 0
}


data class Owner(
        @SerializedName("login") val login: String,
        @SerializedName("id") val id: Int,
        @SerializedName("url") val url: String,
        @SerializedName("followers_url") val followerUrl: String
)

fun GitRepoDTO.toDomain() = GitRepos(
        id = id ?: 0,
        name = name,
        ownerName = owner?.login,
        ownerFollowersUrl = owner?.followerUrl,
        description = description,
        stars = stars,
        fullName = fullName,
        ownerId = owner?.id,
        ownerUrl = owner?.url,
        forks = forks,
        language = language,
        url = url,
        datetime = Instant.parse(date.orEmpty()).toLocalDateTime(TimeZone.UTC)
)

fun GitRepos.toDTO() = GitRepoDTO(
        id = id,
        name = name,
        owner = Owner(login = ownerName.orEmpty(), url = ownerUrl.orEmpty(), id = ownerId ?: 0, followerUrl = ownerFollowersUrl.orEmpty()),
        stars = stars,
        description = description,
        fullName = fullName,
        forks = forks,
        language = language,
        url = url,
        date = datetime?.toInstant(TimeZone.UTC).toString()
)


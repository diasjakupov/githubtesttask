package com.example.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entity.UserInfo
import com.google.gson.annotations.SerializedName


@Entity(tableName = "owner")
data class UserDTO(
        @SerializedName("login") val name: String,
        @SerializedName("followers") val followers: Int?,
        @SerializedName("bio") val bio: String?,
        @SerializedName("avatar_url") val image: String?,
        @SerializedName("public_repos") val publicRepos: Int?
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}


fun UserInfo.toDTO() = UserDTO(
        name = name,
        followers = followers,
        bio = bio,
        image = image,
        publicRepos = publicRepos
)

fun UserDTO.toDomain() = UserInfo(
        name = name.orEmpty(),
        followers = followers ?: 0,
        bio = bio.orEmpty(),
        image = image.orEmpty(),
        publicRepos = publicRepos ?: 0
)

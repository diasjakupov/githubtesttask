package com.example.presentation.uimodels

import com.example.domain.entity.UserInfo


data class UserUiModel(
        val profilePictureUrl: String,
        val name: String,
        val bio: String,
        val followers: Int
)


fun UserInfo.toUIModel() = UserUiModel(
        profilePictureUrl = image,
        name = name,
        bio = bio,
        followers = followers
)
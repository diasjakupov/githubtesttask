package com.example.domain.entity

data class UserInfo(
        val name: String,
        val followers: Int,
        val bio: String,
        val image: String,
        val publicRepos: Int
)

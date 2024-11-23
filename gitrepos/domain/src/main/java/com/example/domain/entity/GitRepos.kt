package com.example.domain.entity

import kotlinx.datetime.LocalDateTime


data class GitRepos(
        val id: Int,
        val name: String?,
        val fullName: String?,
        val ownerId: Int?,
        val ownerName: String?,
        val ownerUrl: String?,
        val description: String?,
        val stars: Int?,
        val forks: Int?,
        val language: String?,
        val url: String?,
        val datetime: LocalDateTime?,
        val ownerFollowersUrl: String?
)
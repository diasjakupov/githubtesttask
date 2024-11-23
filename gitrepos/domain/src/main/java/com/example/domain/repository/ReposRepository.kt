package com.example.domain.repository

import com.example.domain.entity.GitRepos
import com.example.domain.entity.UserInfo

interface ReposRepository {

    suspend fun getReposRemote(query: String, page: Int): Result<List<GitRepos>>
    suspend fun getUserReposRemote(username: String, page: Int): Result<List<GitRepos>>

    suspend fun getViewedRepos(): Result<List<GitRepos>>
    suspend fun markAsViewed(model: GitRepos)
    suspend fun getViewedRepos(query: String): Result<List<GitRepos>>
    suspend fun getViewedRepos(query: String, username: String): Result<List<GitRepos>>


}
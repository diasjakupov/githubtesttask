package com.example.domain.datasource

import com.example.domain.entity.GitRepos
import com.example.domain.entity.UserInfo

interface LocalRepoDataSource {

    suspend fun getRepos(): List<GitRepos>
    suspend fun getRepos(query: String): List<GitRepos>
    suspend fun getRepos(query: String, username: String): List<GitRepos>

    suspend fun getUsers(): List<UserInfo>
    suspend fun getUsers(username: String): List<UserInfo>

    suspend fun addRepo(repo: GitRepos)
    suspend fun addUserInfo(userInfo: UserInfo)
}
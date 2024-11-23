package com.example.domain.datasource

import com.example.domain.entity.GitRepos
import com.example.domain.entity.UserInfo

interface RemoteReposDataSource {

    suspend fun getRepos(query: String, page: Int): List<GitRepos>
    suspend fun getUsersRepos(username: String, page: Int): List<GitRepos>
    suspend fun getUserInfo(username: String): UserInfo
}
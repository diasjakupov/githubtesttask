package com.example.data.datasource

import com.example.data.api.ReposAPI
import com.example.data.dto.GitRepoDTO
import com.example.data.dto.toDomain
import com.example.domain.datasource.RemoteReposDataSource
import com.example.domain.entity.GitRepos
import com.example.domain.entity.UserInfo
import javax.inject.Inject
import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Remote

class RemoteRepoDataSourceRemote @Inject constructor(
        private val api: ReposAPI
) : RemoteReposDataSource {

    override suspend fun getRepos(query: String, page: Int) = api.getRepos(query = query, page = page).items.map(GitRepoDTO::toDomain)
    override suspend fun getUsersRepos(username: String,  page: Int): List<GitRepos> =
            api.getUsersRepos(username = username, page = page).map(GitRepoDTO::toDomain)

    override suspend fun getUserInfo(username: String): UserInfo = api.getUserInfo(username).toDomain()
}
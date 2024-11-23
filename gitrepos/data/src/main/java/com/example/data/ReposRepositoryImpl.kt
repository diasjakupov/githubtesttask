package com.example.data

import com.example.core.base.LoggerRepository
import com.example.core.network.NetworkInterceptor
import com.example.data.datasource.Local
import com.example.data.datasource.Remote
import com.example.domain.datasource.LocalRepoDataSource
import com.example.domain.datasource.RemoteReposDataSource
import com.example.domain.entity.GitRepos
import com.example.domain.repository.ReposRepository
import javax.inject.Inject

class ReposRepositoryImpl @Inject constructor(
        @Remote private val remoteRepoDataSource: RemoteReposDataSource,
        @Local private val localRepoDataSource: LocalRepoDataSource,
        private val networkInterceptor: NetworkInterceptor
): ReposRepository, LoggerRepository {

    override suspend fun getReposRemote(query: String, page: Int): Result<List<GitRepos>> =
            runCatching {
                networkInterceptor.executeWithNetworkCheck {
                    remoteRepoDataSource.getRepos(query, page)
                }
            }.fold(::logSuccess, ::logError)

    override suspend fun getUserReposRemote(username: String, page: Int): Result<List<GitRepos>> =
        runCatching {
            networkInterceptor.executeWithNetworkCheck {
                remoteRepoDataSource.getUsersRepos(username, page)
            }
        }.fold(::logSuccess, ::logError)

    override suspend fun getViewedRepos(): Result<List<GitRepos>> = runCatching{
        localRepoDataSource.getRepos()
    }.fold(::logSuccess, ::logError)

    override suspend fun markAsViewed(model: GitRepos) = kotlin.runCatching {
        localRepoDataSource.addRepo(model)
    }.fold(::logSuccess, ::logError).let { Unit }

    override suspend fun getViewedRepos(query: String): Result<List<GitRepos>> = runCatching{
        localRepoDataSource.getRepos(query)
    }.fold(::logSuccess, ::logError)

    override suspend fun getViewedRepos(query: String, username: String): Result<List<GitRepos>> = runCatching{
        localRepoDataSource.getRepos(query, username)
    }.fold(::logSuccess, ::logError)
}
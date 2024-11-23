package com.example.data

import com.example.core.base.LoggerRepository
import com.example.core.network.NetworkInterceptor
import com.example.data.datasource.Local
import com.example.data.datasource.Remote
import com.example.domain.datasource.LocalRepoDataSource
import com.example.domain.datasource.RemoteReposDataSource
import com.example.domain.entity.UserInfo
import com.example.domain.repository.UsersRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
        @Remote private val remoteRepoDataSource: RemoteReposDataSource,
        @Local private val localRepoDataSource: LocalRepoDataSource,
        private val networkInterceptor: NetworkInterceptor
): UsersRepository, LoggerRepository {


    override suspend fun getUserInfo(username: String): Result<UserInfo> = runCatching {
        networkInterceptor.executeWithNetworkCheck{
            remoteRepoDataSource.getUserInfo(username)
        }
    }.fold(::logSuccess, ::logError)

    override suspend fun markUserAsViewed(userInfo: UserInfo) = runCatching {
        localRepoDataSource.addUserInfo(userInfo)
    }.fold(::logSuccess, ::logError).let { Unit }

    override suspend fun getLocalUserInfos(): Result<List<UserInfo>> = runCatching {
        localRepoDataSource.getUsers()
    }.fold(::logSuccess, ::logError)

    override suspend fun getLocalUserInfos(username: String): Result<List<UserInfo>> = runCatching {
        localRepoDataSource.getUsers(username)
    }.fold(::logSuccess, ::logError)
}
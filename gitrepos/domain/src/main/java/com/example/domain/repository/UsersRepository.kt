package com.example.domain.repository

import com.example.domain.entity.UserInfo

interface UsersRepository {
    suspend fun getUserInfo(username: String): Result<UserInfo>
    suspend fun markUserAsViewed(userInfo: UserInfo)

    suspend fun getLocalUserInfos(): Result<List<UserInfo>>
    suspend fun getLocalUserInfos(username: String): Result<List<UserInfo>>

}
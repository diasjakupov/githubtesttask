package com.example.data.datasource

import android.util.Log
import com.example.data.dao.GitRepoDao
import com.example.data.dao.UserDao
import com.example.data.dto.GitRepoDTO
import com.example.data.dto.UserDTO
import com.example.data.dto.toDTO
import com.example.data.dto.toDomain
import com.example.domain.datasource.LocalRepoDataSource
import com.example.domain.entity.GitRepos
import com.example.domain.entity.UserInfo
import javax.inject.Inject
import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Local

class LocalRepoDataSourceImpl @Inject constructor(
        private val gitRepoDao: GitRepoDao,
        private val gitUserDao: UserDao
) : LocalRepoDataSource {

    override suspend fun getRepos(): List<GitRepos> = gitRepoDao.getAllRepos().map(GitRepoDTO::toDomain)

    override suspend fun getRepos(query: String): List<GitRepos> = gitRepoDao.getReposSearch(query).map(GitRepoDTO::toDomain)

    override suspend fun getRepos(query: String, username: String): List<GitRepos> = gitRepoDao.getReposSearch(query, username).map(GitRepoDTO::toDomain)

    override suspend fun addRepo(repo: GitRepos) = gitRepoDao.insertRepoWithLimit(repo = repo.toDTO())

    override suspend fun getUsers(): List<UserInfo> = gitUserDao.getAllUsers().map(UserDTO::toDomain)

    override suspend fun getUsers(username: String): List<UserInfo> = gitUserDao.getUserSearch(username).map(UserDTO::toDomain)

    override suspend fun addUserInfo(userInfo: UserInfo) = gitUserDao.insertUserWithLimit(userInfo.toDTO())
}
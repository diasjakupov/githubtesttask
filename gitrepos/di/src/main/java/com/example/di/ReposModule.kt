package com.example.di

import com.example.data.ReposRepositoryImpl
import com.example.data.UsersRepositoryImpl
import com.example.data.datasource.Local
import com.example.data.datasource.LocalRepoDataSourceImpl
import com.example.data.datasource.Remote
import com.example.data.datasource.RemoteRepoDataSourceRemote
import com.example.domain.datasource.LocalRepoDataSource
import com.example.domain.datasource.RemoteReposDataSource
import com.example.domain.repository.ReposRepository
import com.example.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReposModule {

    @Binds
    @Singleton
    abstract fun bindReposRepository(reposRepository: ReposRepositoryImpl): ReposRepository

    @Binds
    @Singleton
    abstract fun bindUsersRepository(usersRepository: UsersRepositoryImpl): UsersRepository


    @Binds
    @Singleton
    @Remote
    abstract fun bindRemoteDataStore(dataSource: RemoteRepoDataSourceRemote): RemoteReposDataSource

    @Binds
    @Singleton
    @Local
    abstract fun bindLocalDataSource(dataSourceRemote: LocalRepoDataSourceImpl): LocalRepoDataSource
}
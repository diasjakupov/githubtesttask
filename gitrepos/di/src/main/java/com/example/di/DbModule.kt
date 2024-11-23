package com.example.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.GitReposDB
import com.example.data.db.GitReposDB.Companion.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideDB(@ApplicationContext context: Context) = Room.databaseBuilder(
            context,
            GitReposDB::class.java, DB_NAME
    ).build()

    @Singleton
    @Provides
    fun provideGitDao(db: GitReposDB) = db.gitReposDao()

    @Singleton
    @Provides
    fun provideUserDao(db: GitReposDB) = db.getOwnerDao()
}


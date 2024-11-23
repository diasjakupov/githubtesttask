package com.example.di

import com.example.data.datasource.AuthLocalDataSourceImpl
import com.example.data.oauth.OAuthManagerImpl
import com.example.data.repository.OAuthRepositoryImpl
import com.example.domain.oauth.AuthLocalDataSource
import com.example.domain.oauth.OAuthManager
import com.example.domain.repository.OAuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.Interceptor


@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: OAuthRepositoryImpl): OAuthRepository

    @Binds
    @Singleton
    abstract fun bindOAuthManager(impl: OAuthManagerImpl): OAuthManager

    @Binds
    @Singleton
    abstract fun bindAuthLocalDataSource(impl: AuthLocalDataSourceImpl): AuthLocalDataSource
}


@Module
@InstallIn(SingletonComponent::class)
object AuthProvidersModule {

    @Provides
    @Auth
    fun provideAuthInterceptor(authDataStore: AuthLocalDataSource): Interceptor =
            com.example.data.oauth.AuthInterceptor(authDataStore)

    @Provides
    @Singleton
    fun provideOAuthProvider(): OAuthProvider = OAuthManagerImpl.provider

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

}

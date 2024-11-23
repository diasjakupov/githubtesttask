package com.example.core

import com.example.core.network.DefaultNetworkInterceptor
import com.example.core.network.NetworkInterceptor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {

    @Binds
    abstract fun bindInternetConnectionInterceptor(impl: DefaultNetworkInterceptor): NetworkInterceptor
}
package com.example.di

import com.example.core.network.UnauthorizedInterceptor
import com.example.data.api.ReposAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
            @Auth authInterceptor: Interceptor,
            @ApplicationContext context: android.content.Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(UnauthorizedInterceptor(context))
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BASIC
                })
                .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }


    @Singleton
    @Provides
    fun provideReposAPI(retrofit: Retrofit): ReposAPI = retrofit.create(ReposAPI::class.java)
}



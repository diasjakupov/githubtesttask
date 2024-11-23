package com.example.data.api

import com.example.data.dto.GitRepoDTO
import com.example.data.dto.GitRepoResponse
import com.example.data.dto.UserDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReposAPI {
        @GET("search/repositories")
        suspend fun getRepos(
                @Query("q") query: String,
                @Query("page") page: Int
        ): GitRepoResponse

        @GET("/users/{username}/repos")
        suspend fun getUsersRepos(
                @Path("username") username: String,
                @Query("page") page: Int
        ): List<GitRepoDTO>

        @GET("users/{username}")
        suspend fun getUserInfo(
                @Path("username") username: String
        ): UserDTO
}
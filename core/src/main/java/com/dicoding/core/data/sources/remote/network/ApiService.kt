package com.dicoding.core.data.sources.remote.network

import com.dicoding.core.BuildConfig
import com.dicoding.core.data.sources.remote.response.SearchResponse
import com.dicoding.core.data.sources.remote.response.UserResponse
import retrofit2.http.*

interface ApiService {
    @GET("users")
    @Headers("Authorization: token ${BuildConfig.ACCESS_TOKEN}")
    suspend fun getAllUser(): List<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.ACCESS_TOKEN}")
    suspend fun getDetailUser(
        @Path("username") username: String,
    ): UserResponse

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.ACCESS_TOKEN}")
    suspend fun searchUser(
        @Query("q") username: String,
        @Query("page") page: Int?=1,
        @Query("per_page") perPage: Int?=10
    ): SearchResponse
}
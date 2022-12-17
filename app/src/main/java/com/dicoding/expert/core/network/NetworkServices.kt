package com.dicoding.expert.core.network

import com.dicoding.expert.BuildConfig
import com.dicoding.expert.data.models.Repo
import com.dicoding.expert.data.models.SearchResponse
import com.dicoding.expert.data.models.User
import retrofit2.http.*

interface NetworkServices {
    @GET("users/")
    @Headers("Authorization: token ${BuildConfig.ACCESS_TOKEN}")
    suspend fun getListUser(
        @Path("username") username: String,
    ): List<User>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.ACCESS_TOKEN}")
    suspend fun getDetailUser(
        @Path("username") username: String,
    ): User

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.ACCESS_TOKEN}")
    suspend fun searchUser(
        @Query("q") username: String,
        @Query("page") page: Int?=1,
        @Query("per_page") perPage: Int?=10
    ): SearchResponse
}
package com.dicoding.expert.core.data.sources.remote

import android.util.Log
import com.dicoding.expert.core.utils.AppConst
import com.dicoding.expert.core.data.sources.remote.network.ApiService
import com.dicoding.expert.data.models.UserResponse
import com.dicoding.expert.core.data.sources.remote.network.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllUser(): Flow<ApiResponse<List<UserResponse>>> {
        return flow {
            try {
                val response = apiService.getAllUser()
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailUser(username: String): Flow<ApiResponse<UserResponse>> {
        return flow {
            try {
                val response = apiService.getDetailUser(username)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchUser(username: String): Flow<ApiResponse<List<UserResponse>>> {
        return flow {
            try {
                val response = apiService.searchUser(username)
                if (response.data.isEmpty()) {
                    emit(ApiResponse.Error(AppConst.userNotFound))
                } else {
                    emit(ApiResponse.Success(response.data))
                }
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}
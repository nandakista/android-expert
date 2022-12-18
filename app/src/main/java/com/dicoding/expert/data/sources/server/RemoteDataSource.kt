package com.dicoding.expert.data.sources.server

import android.util.Log
import com.dicoding.expert.core.utils.AppConst
import com.dicoding.expert.core.network.ApiService
import com.dicoding.expert.data.models.UserResponse
import com.dicoding.expert.data.models.base.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null
        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }.also { instance = it }
    }

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
package com.dicoding.expert.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.expert.core.network.NetworkServices
import com.dicoding.expert.data.models.User
import com.dicoding.expert.data.models.base.ApiResult
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class DetailUserRepository private constructor(
    private val apiService: NetworkServices,
) {
    companion object {
        @Volatile
        private var instance: DetailUserRepository? = null
        fun getInstance(apiService: NetworkServices): DetailUserRepository =
            instance ?: synchronized(this) {
                instance ?: DetailUserRepository(apiService)
            }.also { instance = it }
    }

    fun getDetailUser(username: String): LiveData<ApiResult<User>> = liveData(Dispatchers.IO) {
        emit(ApiResult.Loading)
        try {
            val response = apiService.getDetailUser(username)
            emit(ApiResult.Success(response))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message.toString()))
        }
    }
}
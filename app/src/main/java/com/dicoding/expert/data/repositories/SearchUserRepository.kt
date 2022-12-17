package com.dicoding.expert.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.expert.core.AppConst
import com.dicoding.expert.core.network.NetworkServices
import com.dicoding.expert.data.models.User
import com.dicoding.expert.data.models.base.ApiResult
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class SearchUserRepository private constructor(private val apiService: NetworkServices){
    companion object {
        @Volatile
        private var instance: SearchUserRepository? = null
        fun getInstance(apiService: NetworkServices): SearchUserRepository =
            instance ?: synchronized(this) {
                instance ?: SearchUserRepository(apiService)
            }.also { instance = it }
    }

    fun searchUser(username: String): LiveData<ApiResult<List<User>>> = liveData(Dispatchers.IO) {
        emit(ApiResult.Loading)
        try {
            val response = apiService.searchUser(username)
            if(response.data.isEmpty()) {
                emit(ApiResult.Error(AppConst.userNotFound))
            } else {
                emit(ApiResult.Success(response.data))
            }
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message.toString()))
        }
    }
}
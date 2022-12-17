package com.dicoding.expert.core.di

import android.content.Context
import com.dicoding.expert.core.database.room.AppDatabase
import com.dicoding.expert.core.network.NetworkConfig
import com.dicoding.expert.data.repositories.DetailUserRepository
import com.dicoding.expert.data.repositories.LocalUserRepository
import com.dicoding.expert.data.repositories.SearchUserRepository

object Injection {
    fun searchUserRepository(): SearchUserRepository {
        val apiService = NetworkConfig.getClient()
        return SearchUserRepository.getInstance(apiService)
    }

    fun detailUserRepository(): DetailUserRepository {
        val apiService = NetworkConfig.getClient()
        return DetailUserRepository.getInstance(apiService)
    }

    fun localUserRepository(context: Context): LocalUserRepository {
        val database = AppDatabase.getInstance(context)
        val dao = database.userDao()
        return LocalUserRepository.getInstance(dao)
    }
}
package com.dicoding.expert.di

import android.content.Context
import com.dicoding.expert.core.database.room.AppDatabase
import com.dicoding.expert.core.network.ApiConfig
import com.dicoding.expert.data.repositories.UserRepository
import com.dicoding.expert.data.sources.local.LocalDataSource
import com.dicoding.expert.data.sources.server.RemoteDataSource
import com.dicoding.expert.domain.repositories.IUserRepository
import com.dicoding.expert.domain.usecases.UserInteractor
import com.dicoding.expert.domain.usecases.UserUseCase

object Injection {
    private fun provideRepository(context: Context): IUserRepository {
        val database = AppDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.userDao())

        return UserRepository.getInstance(remoteDataSource, localDataSource)
    }

    fun provideUserUseCase(context: Context): UserUseCase {
        val repository = provideRepository(context)
        return UserInteractor(repository)
    }
}
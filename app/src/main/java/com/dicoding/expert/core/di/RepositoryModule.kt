package com.dicoding.expert.core.di

import com.dicoding.expert.core.data.UserRepository
import com.dicoding.expert.core.di.DatabaseModule
import com.dicoding.expert.core.di.NetworkModule
import com.dicoding.expert.core.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(userRepository: UserRepository): IUserRepository
}
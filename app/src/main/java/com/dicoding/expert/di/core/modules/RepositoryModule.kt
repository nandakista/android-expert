package com.dicoding.expert.di.core.modules

import com.dicoding.expert.data.repositories.UserRepository
import com.dicoding.expert.di.core.modules.DatabaseModule
import com.dicoding.expert.di.core.modules.NetworkModule
import com.dicoding.expert.domain.repositories.IUserRepository
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
package com.dicoding.expert.di.app

import com.dicoding.expert.domain.usecases.UserInteractor
import com.dicoding.expert.domain.usecases.UserUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {
    @Binds
    abstract fun provideUserUseCase(userInteractor: UserInteractor): UserUseCase
}
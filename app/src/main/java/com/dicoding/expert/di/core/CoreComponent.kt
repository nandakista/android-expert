package com.dicoding.expert.di.core

import android.content.Context
import com.dicoding.expert.di.core.modules.RepositoryModule
import com.dicoding.expert.domain.repositories.IUserRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class]
)

interface CoreComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun provideRepository() : IUserRepository
}
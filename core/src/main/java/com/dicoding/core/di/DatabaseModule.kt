package com.dicoding.core.di

import android.content.Context
import androidx.room.Room
import com.dicoding.core.data.sources.local.room.AppDatabase
import com.dicoding.core.data.sources.local.room.UserDao
import com.dicoding.core.utils.AppConst
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, AppConst.DB_NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()
}
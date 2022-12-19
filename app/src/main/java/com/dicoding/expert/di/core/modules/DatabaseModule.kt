package com.dicoding.expert.di.core.modules

import android.content.Context
import androidx.room.Room
import com.dicoding.expert.core.database.room.AppDatabase
import com.dicoding.expert.core.database.room.UserDao
import com.dicoding.expert.core.utils.AppConst
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
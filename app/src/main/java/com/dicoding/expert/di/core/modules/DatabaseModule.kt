package com.dicoding.expert.di.core.modules

import android.content.Context
import androidx.room.Room
import com.dicoding.expert.core.database.room.AppDatabase
import com.dicoding.expert.core.database.room.UserDao
import com.dicoding.expert.core.utils.AppConst
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, AppConst.DB_NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()
}
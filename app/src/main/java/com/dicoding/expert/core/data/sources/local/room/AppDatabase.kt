package com.dicoding.expert.core.data.sources.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.expert.core.utils.AppConst
import com.dicoding.expert.core.data.sources.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = AppConst.DB_VERSION,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}
package com.dicoding.core.data.sources.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.core.data.sources.local.entity.UserEntity
import com.dicoding.core.utils.AppConst

@Database(
    entities = [UserEntity::class],
    version = AppConst.DB_VERSION,
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}
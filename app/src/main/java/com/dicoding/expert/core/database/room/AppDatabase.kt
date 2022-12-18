package com.dicoding.expert.core.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.expert.core.utils.AppConst
import com.dicoding.expert.core.database.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = AppConst.DB_VERSION
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}
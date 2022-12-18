package com.dicoding.expert.data.sources.local

import com.dicoding.expert.core.database.entity.UserEntity
import com.dicoding.expert.core.database.room.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class LocalDataSource private constructor(private val userDao: UserDao) {
    companion object {
        @Volatile
        private var instance: LocalDataSource? = null
        fun getInstance(userDao: UserDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(userDao)
            }.also { instance = it }
    }

    fun getFavUser(): Flow<List<UserEntity>> {
        return userDao.getAll()
    }

    fun setFavUser(users: UserEntity): Flow<String> {
        return flow {
            try {
                userDao.insert(users)
                emit("Success Added")
            } catch (e: Exception) {
                emit("Failed Added")
            }
        }.flowOn(Dispatchers.IO)
    }

    fun hasAdded(id: Int): Flow<Boolean> {
        return flow{
            emit(userDao.hasAdded(id))
        }.flowOn(Dispatchers.IO)
    }

    fun deleteFavUser(id: Int): Flow<String> {
        return flow {
            try {
                userDao.deleteById(id)
                emit("Success Delete")
            } catch (e: Exception) {
                emit("Failed Delete")
            }
        }.flowOn(Dispatchers.IO)
    }
}
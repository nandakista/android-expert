package com.dicoding.expert.data.repositories

import androidx.lifecycle.LiveData
import com.dicoding.expert.core.database.entity.UserEntity
import com.dicoding.expert.core.database.room.UserDao

class LocalUserRepository private constructor(private val userDao: UserDao){
    companion object {
        @Volatile
        private var instance: LocalUserRepository? = null
        fun getInstance(userDao: UserDao): LocalUserRepository =
            instance ?: synchronized(this) {
                instance ?: LocalUserRepository(userDao)
            }.also { instance = it }
    }

    fun getFavUser(): LiveData<List<UserEntity>> {
        return userDao.getAll()
    }

    suspend fun setFavUser(users: UserEntity) {
        return userDao.insert(users)
    }

    suspend fun hasAdded(id: Int): Boolean {
        return userDao.hasAdded(id)
    }

    suspend fun deleteFavUser(id: Int) {
        return userDao.deleteById(id)
    }
}
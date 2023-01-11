package com.dicoding.core.domain.repository

import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getAllUser(): Flow<Resource<List<User>>>

    fun getDetailUser(username: String): Flow<Resource<User>>

    fun searchUser(username: String): Flow<Resource<List<User>>>

    fun getFavoriteUser(): Flow<List<User>>

    fun setFavoriteUser(user: User): Flow<String>

    fun hasAdded(id: Int): Flow<Boolean>

    fun deleteFavUser(id: Int): Flow<String>
}
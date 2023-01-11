package com.dicoding.expert.core.domain.usecase

import com.dicoding.expert.core.data.Resource
import com.dicoding.expert.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun getAllUser(): Flow<Resource<List<User>>>

    fun getDetailUser(username: String): Flow<Resource<User>>

    fun searchUser(username: String): Flow<Resource<List<User>>>

    fun getFavoriteUser(): Flow<List<User>>

    fun setFavoriteUser(user: User): Flow<String>

    fun hasAdded(id: Int): Flow<Boolean>

    fun deleteFavUser(id: Int): Flow<String>
}
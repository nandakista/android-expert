package com.dicoding.expert.core.domain.usecase

import com.dicoding.expert.core.data.Resource
import com.dicoding.expert.core.domain.model.User
import com.dicoding.expert.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: IUserRepository): UserUseCase {
    override fun getAllUser(): Flow<Resource<List<User>>> {
        return userRepository.getAllUser()
    }

    override fun getDetailUser(username: String): Flow<Resource<User>> {
        return userRepository.getDetailUser(username)
    }

    override fun searchUser(username: String): Flow<Resource<List<User>>> {
        return userRepository.searchUser(username)
    }

    override fun getFavoriteUser(): Flow<List<User>> {
        return userRepository.getFavoriteUser()
    }

    override fun setFavoriteUser(user: User): Flow<String> {
        return userRepository.setFavoriteUser(user)
    }

    override fun hasAdded(id: Int): Flow<Boolean> {
        return userRepository.hasAdded(id)
    }

    override fun deleteFavUser(id: Int): Flow<String> {
        return userRepository.deleteFavUser(id)
    }
}
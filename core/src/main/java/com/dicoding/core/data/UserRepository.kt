package com.dicoding.core.data

import com.dicoding.core.data.sources.local.LocalDataSource
import com.dicoding.core.data.sources.remote.RemoteDataSource
import com.dicoding.core.data.sources.remote.network.ApiResponse
import com.dicoding.core.domain.model.User
import com.dicoding.core.domain.repository.IUserRepository
import com.dicoding.core.utils.DataMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IUserRepository {

    override fun getAllUser(): Flow<com.dicoding.core.data.Resource<List<User>>> {
        return flow {
            emit(com.dicoding.core.data.Resource.Loading())
            when (val it = remoteDataSource.getAllUser().first()) {
                is ApiResponse.Success -> {
                    val userList = DataMapper.userResponseToUser(it.data)
                    emit(com.dicoding.core.data.Resource.Success(userList))
                }
                is ApiResponse.Error -> {
                    emit(com.dicoding.core.data.Resource.Error(it.errMsg))
                }
            }
        }
    }


    override fun getDetailUser(username: String): Flow<com.dicoding.core.data.Resource<User>> {
        return flow {
            emit(com.dicoding.core.data.Resource.Loading())
            when (val it = remoteDataSource.getDetailUser(username).first()) {
                is ApiResponse.Success -> {
                    emit(com.dicoding.core.data.Resource.Success(DataMapper.mapUserResponseToUser(it.data)))
                }
                is ApiResponse.Error -> {
                    emit(com.dicoding.core.data.Resource.Error(it.errMsg))
                }
            }
        }
    }

    override fun searchUser(username: String): Flow<com.dicoding.core.data.Resource<List<User>>> {
        return flow {
            emit(com.dicoding.core.data.Resource.Loading())
            when (val it = remoteDataSource.searchUser(username).first()) {
                is ApiResponse.Success -> {
                    val userList = DataMapper.userResponseToUser(it.data)
                    emit(com.dicoding.core.data.Resource.Success(userList))
                }
                is ApiResponse.Error -> {
                    emit(com.dicoding.core.data.Resource.Error(it.errMsg))
                }
            }
        }
    }

    override fun getFavoriteUser(): Flow<List<User>> {
        return localDataSource.getFavUser().map {
            DataMapper.userEntityToUser(it)
        }
    }

    override fun setFavoriteUser(user: User): Flow<String> {
        val userEntity = DataMapper.userToUserEntity(user)
        return localDataSource.setFavUser(userEntity)
    }

    override fun hasAdded(id: Int): Flow<Boolean> {
        return localDataSource.hasAdded(id)
    }

    override fun deleteFavUser(id: Int): Flow<String> {
        return localDataSource.deleteFavUser(id)
    }
}
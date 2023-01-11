package com.dicoding.expert.core.data

import com.dicoding.expert.core.utils.DataMapper
import com.dicoding.expert.core.data.sources.remote.network.ApiResponse
import com.dicoding.expert.core.data.sources.local.LocalDataSource
import com.dicoding.expert.core.data.sources.remote.RemoteDataSource
import com.dicoding.expert.core.domain.model.User
import com.dicoding.expert.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IUserRepository {

    override fun getAllUser(): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Loading())
            when (val it = remoteDataSource.getAllUser().first()) {
                is ApiResponse.Success -> {
                    val userList = DataMapper.userResponseToUser(it.data)
                    emit(Resource.Success(userList))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(it.errMsg))
                }
            }
        }
    }


    override fun getDetailUser(username: String): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading())
            when (val it = remoteDataSource.getDetailUser(username).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(DataMapper.mapUserResponseToUser(it.data)))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(it.errMsg))
                }
            }
        }
    }

    override fun searchUser(username: String): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Loading())
            when (val it = remoteDataSource.searchUser(username).first()) {
                is ApiResponse.Success -> {
                    val userList = DataMapper.userResponseToUser(it.data)
                    emit(Resource.Success(userList))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(it.errMsg))
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
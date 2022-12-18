package com.dicoding.expert.core.utils

import com.dicoding.expert.core.database.entity.UserEntity
import com.dicoding.expert.data.models.UserResponse
import com.dicoding.expert.domain.entities.User

object DataMapper {
    fun mapUserResponseToUser(input: UserResponse): User {
        return User(
            id = input.id,
            name = input.name,
            username = input.username,
            userType = input.userType,
            avatarUrl = input.avatarUrl,
            company = input.company,
            gitUrl = input.gitUrl,
            location = input.location,
            biodata = input.biodata,
            followers = input.followers,
            following = input.following,
            repository = input.repository,
            repoUrl = input.repoUrl,
        )
    }

    fun userResponseToUser(input: List<UserResponse>): List<User> {
        val userList = ArrayList<User>()
        input.map {
            val user = User(
                id = it.id,
                name = it.name,
                username = it.username,
                userType = it.userType,
                avatarUrl = it.avatarUrl,
                company = it.company,
                gitUrl = it.gitUrl,
                location = it.location,
                biodata = it.biodata,
                followers = it.followers,
                following = it.following,
                repository = it.repository,
                repoUrl = it.repoUrl,
            )
            userList.add(user)
        }
        return userList
    }

    fun userEntityToUser(input: List<UserEntity>): List<User> =
        input.map {
            User(
                id = it.id,
                name = it.name,
                username = it.username,
                userType = it.userType,
                avatarUrl = it.avatarUrl,
                company = it.company,
                gitUrl = it.gitUrl,
                location = it.location,
                biodata = it.biodata,
                followers = it.followers,
                following = it.following,
                repository = it.repository,
                repoUrl = it.repoUrl,
            )
        }

    fun userToUserEntity(user: User) = UserEntity(
        id = user.id,
        name = user.name,
        username = user.username,
        userType = user.userType,
        avatarUrl = user.avatarUrl,
        company = user.company,
        gitUrl = user.gitUrl,
        location = user.location,
        biodata = user.biodata,
        followers = user.followers,
        following = user.following,
        repository = user.repository,
        repoUrl = user.repoUrl,
    )
}
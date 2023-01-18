package com.dicoding.expert.ui.pages

import com.dicoding.core.data.sources.remote.response.UserResponse
import com.dicoding.core.domain.model.User

object DataDummy {
    fun getDummyListUserResponse(): List<UserResponse> {
        val list = ArrayList<UserResponse>()
        for (i in 0..10) {
            val userResponse = UserResponse(
                i,
                "username",
                "name",
                "location",
                "company",
                "user_type",
                "git_url",
                "bio",
                "avatar_url",
                "repo_url",
                1,
                1,
                1,
            )
            list.add(userResponse)
        }
        return list
    }

    fun getDummyUserResponse(): UserResponse {
        return UserResponse(
            1,
            "username",
            "name",
            "location",
            "company",
            "user_type",
            "git_url",
            "bio",
            "avatar_url",
            "repo_url",
            1,
            1,
            1,
        )
    }

    fun getDummyListUser(): List<User> {
        val list = ArrayList<User>()
        for (i in 0..10) {
            val userResponse = User(
                i,
                "username",
                "name",
                "location",
                "company",
                "user_type",
                "git_url",
                "bio",
                "avatar_url",
                "repo_url",
                1,
                1,
                1,
            )
            list.add(userResponse)
        }
        return list
    }

    fun getDummyUser(): User {
        return User(
            1,
            "username",
            "name",
            "location",
            "company",
            "user_type",
            "git_url",
            "bio",
            "avatar_url",
            "repo_url",
            1,
            1,
            1,
        )
    }
}
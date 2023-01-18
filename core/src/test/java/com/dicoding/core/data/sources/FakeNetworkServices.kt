package com.dicoding.core.data.sources

import com.dicoding.core.data.sources.remote.network.ApiService
import com.dicoding.core.data.sources.remote.response.UserResponse
import com.dicoding.core.DataDummy

class FakeNetworkServices : ApiService {
    override suspend fun getAllUser(): List<UserResponse> {
        return DataDummy.getDummyListUserResponse()
    }

    override suspend fun getDetailUser(username: String): UserResponse {
        return DataDummy.getDummyUserResponse()
    }
}
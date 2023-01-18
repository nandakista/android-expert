package com.dicoding.core.data.sources

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.core.data.MainDispatcherRule
import com.dicoding.core.data.sources.remote.RemoteDataSource
import com.dicoding.core.data.sources.remote.network.ApiResponse
import com.dicoding.core.data.sources.remote.network.ApiService
import com.dicoding.core.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RemoteDataSourceTest {

    private lateinit var dataSource: RemoteDataSource
    private lateinit var apiService: ApiService

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        apiService = FakeNetworkServices()
        dataSource = RemoteDataSource(apiService)
    }

    @Test
    fun `when getAllUser, should not null`() = runTest {
        val expected = DataDummy.getDummyListUserResponse()
        dataSource.getAllUser().collect { actual ->
            Assert.assertNotNull(actual)
            Assert.assertEquals(expected, (actual as ApiResponse.Success).data)
        }
    }

    @Test
    fun `when getUser, should not null`() = runTest {
        val expected = DataDummy.getDummyUserResponse()
        dataSource.getDetailUser("dummy").collect { actual ->
            Assert.assertNotNull(actual)
            Assert.assertEquals(expected, (actual as ApiResponse.Success).data)
        }
    }
}



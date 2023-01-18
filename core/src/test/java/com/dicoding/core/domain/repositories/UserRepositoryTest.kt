package com.dicoding.core.domain.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.core.data.UserRepository
import com.dicoding.core.data.MainDispatcherRule
import com.dicoding.core.data.Resource
import com.dicoding.core.data.sources.local.LocalDataSource
import com.dicoding.core.data.sources.remote.RemoteDataSource
import com.dicoding.core.data.sources.remote.network.ApiResponse
import com.dicoding.core.DataDummy
import com.dicoding.core.utils.DataMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {
    private lateinit var repository: UserRepository

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var localDataSource: LocalDataSource

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        repository = UserRepository(remoteDataSource, localDataSource)
    }

    private val dummyDataUserResponseList = DataDummy.getDummyListUserResponse()
    private val dummyDataUserResponse = DataDummy.getDummyUserResponse()

    @Test
    fun `when Get All User Should Not Null and Return Success`() {
        runBlocking {
            val response = ApiResponse.Success(dummyDataUserResponseList)
            Mockito.`when`(remoteDataSource.getAllUser())
                .thenReturn(flowOf(response))
            val expected = DataMapper.userResponseToUser(response.data)
            val actual = repository.getAllUser().last()
            Mockito.verify(remoteDataSource).getAllUser()
            Assert.assertNotNull(actual)
            Assert.assertTrue(actual is Resource.Success)
            Assert.assertEquals(expected.size, (actual).data?.size)
        }
    }

    @Test
    fun `when Get All User and Network Error Should Return Error`() {
        runBlocking {
            val response = ApiResponse.Error("error")
            Mockito.`when`(remoteDataSource.getAllUser())
                .thenReturn(flowOf(response))
            val actual = repository.getAllUser().last()
            Mockito.verify(remoteDataSource).getAllUser()
            Assert.assertNotNull(actual)
            Assert.assertTrue(actual is Resource.Error)
        }
    }

    @Test
    fun `when Get Detail User Should Not Null and Return Success`() {
        runBlocking {
            val response = ApiResponse.Success(dummyDataUserResponse)
            Mockito.`when`(remoteDataSource.getDetailUser("dummy"))
                .thenReturn(flowOf(response))
            val expected = DataMapper.mapUserResponseToUser(response.data)
            val actual = repository.getDetailUser("dummy").last()
            Mockito.verify(remoteDataSource).getDetailUser("dummy")
            Assert.assertNotNull(actual)
            Assert.assertTrue(actual is Resource.Success)
            Assert.assertEquals(expected, (actual).data)
        }
    }

    @Test
    fun `when Get Detail User and Network Error Should Return Error`() {
        runBlocking {
            val response = ApiResponse.Error("error")
            Mockito.`when`(remoteDataSource.getDetailUser("dummy"))
                .thenReturn(flowOf(response))
            val actual = repository.getDetailUser("dummy").last()
            Mockito.verify(remoteDataSource).getDetailUser("dummy")
            Assert.assertNotNull(actual)
            Assert.assertTrue(actual is Resource.Error)
        }
    }
}
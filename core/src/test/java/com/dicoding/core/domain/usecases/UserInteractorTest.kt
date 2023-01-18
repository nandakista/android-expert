package com.dicoding.core.domain.usecases

import com.dicoding.core.data.Resource
import com.dicoding.core.data.UserRepository
import com.dicoding.core.domain.usecase.UserInteractor
import com.dicoding.core.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserInteractorTest {
    private lateinit var userIteractor: UserInteractor

    @Mock
    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        userIteractor = UserInteractor(repository)
    }

    private val dummyDataUserList = DataDummy.getDummyListUser()
    private val dummyDataUser = DataDummy.getDummyUser()

    @Test
    fun `when Get All User Should Not Null and Return Success`() = runTest {
        val expected = Resource.Success(dummyDataUserList)
        Mockito.`when`(repository.getAllUser())
            .thenReturn(flowOf(expected))
        userIteractor.getAllUser().collect { actual ->
            Assert.assertNotNull(actual)
            Assert.assertTrue(actual is Resource.Success)
            Assert.assertEquals(expected.data?.size, (actual as Resource.Success).data?.size)
        }
    }

    @Test
    fun `when Get User Should Not Null and Return Success`() = runTest {
        val expected = Resource.Success(dummyDataUser)
        Mockito.`when`(repository.getDetailUser("dummy"))
            .thenReturn(flowOf(expected))
        userIteractor.getDetailUser("dummy").collect { actual ->
            Assert.assertNotNull(actual)
            Assert.assertTrue(actual is Resource.Success)
            Assert.assertEquals(expected.data, (actual as Resource.Success).data)
        }
    }
}
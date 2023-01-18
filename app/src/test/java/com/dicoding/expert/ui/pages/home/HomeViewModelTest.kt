package com.dicoding.expert.ui.pages.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.core.data.Resource
import com.dicoding.core.domain.model.User
import com.dicoding.core.domain.usecase.UserUseCase
import com.dicoding.expert.ui.pages.DataDummy
import com.dicoding.expert.ui.pages.MainDispatcherRule
import com.dicoding.expert.ui.pages.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var useCase: UserUseCase

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = HomeViewModel(useCase)
    }

    @Test
    fun `when getAllUser, should return Resource Success with list user data`() {
        val expected = Resource.Success(DataDummy.getDummyListUser())
        Mockito.`when`(useCase.getAllUser()).thenReturn(flowOf(expected))
        val actual = viewModel.getAllUser.getOrAwaitValue()

        Mockito.verify(useCase).getAllUser()
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Resource.Success)
        Assert.assertEquals(expected.data, (actual as Resource.Success).data)
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val expected = Resource.Error<List<User>>("Error")
        Mockito.`when`(useCase.getAllUser()).thenReturn(flowOf(expected))
        val actual = viewModel.getAllUser.getOrAwaitValue()

        Mockito.verify(useCase).getAllUser()
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Resource.Error)
    }
}
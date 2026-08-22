package com.mysocialapp.android.ui.home

import com.mysocialapp.android.data.repository.HealthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    @Before fun setUp() { Dispatchers.setMain(dispatcher) }
    @After fun tearDown() { Dispatchers.resetMain() }
    @Test fun checkApi_exposesHealthyStatus() = runTest { val repository = object : HealthRepository { override suspend fun checkHealth() = Result.success("healthy") }; val viewModel = HomeViewModel(repository); viewModel.checkApi(); dispatcher.scheduler.advanceUntilIdle(); assertEquals("healthy", viewModel.state.value.healthStatus) }
}

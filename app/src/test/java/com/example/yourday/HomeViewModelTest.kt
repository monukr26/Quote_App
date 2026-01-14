package com.example.yourday

import com.example.yourday.model.Quote
import com.example.yourday.presentation.home.HomeViewModel
import com.example.yourday.usecase.GetRandomQuotesCase
import com.example.yourday.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val mockUseCase = mockk<GetRandomQuotesCase>(relaxed = true)
    private lateinit var viewModel: HomeViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(mockUseCase, mockk(relaxed = true))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchQuote updates state with success`() = runTest {
        val quote = Quote("Test Quote", "Tester")
        coEvery { mockUseCase.invoke() } returns flow {
            emit(Resource.Success(quote))
        }

        viewModel.fetchQuote()

        val state = viewModel.state.value
        assertThat(state.quote).isEqualTo(quote)
        assertThat(state.isLoading).isFalse()
    }
}
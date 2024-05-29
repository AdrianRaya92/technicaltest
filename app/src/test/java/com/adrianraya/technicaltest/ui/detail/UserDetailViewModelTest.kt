package com.adrianraya.technicaltest.ui.detail

import app.cash.turbine.test
import com.adrianraya.domain.Error
import com.adrianraya.technicaltest.testrules.CoroutinesTestRule
import com.adrianraya.testshared.sampleUserList
import com.adrianraya.usecase.userdetail.FindUserUseCase
import com.adrianraya.technicaltest.ui.detail.UserDetailViewModel.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserDetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var findUserUseCase: FindUserUseCase

    private lateinit var vm: UserDetailViewModel

    private val user = sampleUserList.copy(id = 1)

    @Test
    fun `UI is updated with the user on start`() = runTest {
        vm = buildViewModel()
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(user = user), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Error is emitted when user search fails`() = runTest {
        whenever(findUserUseCase(1)).thenReturn(flow { throw IOException("Test Error") })
        val vm = UserDetailViewModel(1, findUserUseCase)

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            val state = awaitItem()
            assertTrue(state.error is Error.Connectivity)
            cancel()
        }
    }

    private fun buildViewModel(): UserDetailViewModel {
        whenever(findUserUseCase(1)).thenReturn(flowOf(user))
        return UserDetailViewModel(1, findUserUseCase)
    }
}

package com.adrianraya.technicaltest.ui.list

import app.cash.turbine.test
import com.adrianraya.technicaltest.testrules.CoroutinesTestRule
import com.adrianraya.technicaltest.ui.list.UsersListViewModel.UiState
import com.adrianraya.testshared.sampleUserList
import com.adrianraya.usecase.userlist.GetUsersListTotalUseCase
import com.adrianraya.usecase.userlist.GetUsersListUseCase
import com.adrianraya.usecase.userlist.RequestUsersListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserListViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getUsersListUseCase: GetUsersListUseCase

    @Mock
    lateinit var getUsersListTotalUseCase: GetUsersListTotalUseCase

    @Mock
    lateinit var requestUsersListUseCase: RequestUsersListUseCase

    private lateinit var vm: UsersListViewModel

    private val users = listOf(sampleUserList.copy(id = 1))

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        vm = buildViewModel()

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(users = users), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Progress is shown when screen starts and hidden when it finishes requesting users`() =
        runTest {
            vm = buildViewModel()
            vm.onUiReady()

            vm.state.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(users = users), awaitItem())
                assertEquals(
                    UiState(
                        users = users,
                        loading = true,
                        totalUsers = "0"
                    ),
                    awaitItem()
                )
                assertEquals(
                    UiState(
                        users = users,
                        loading = false,
                        totalUsers = "4"
                    ),
                    awaitItem()
                )
                cancel()
            }
        }

    @Test
    fun `Users are requested when UI screen starts`() = runTest {
        vm = buildViewModel()
        vm.onUiReady()
        runCurrent()
        verify(requestUsersListUseCase).invoke()
    }

    private fun buildViewModel(): UsersListViewModel {
        whenever(getUsersListUseCase()).thenReturn(flowOf(users))
        runBlocking {
            whenever(getUsersListTotalUseCase.invoke()).thenReturn(4)
        }
        return UsersListViewModel(getUsersListUseCase, requestUsersListUseCase, getUsersListTotalUseCase)
    }
}

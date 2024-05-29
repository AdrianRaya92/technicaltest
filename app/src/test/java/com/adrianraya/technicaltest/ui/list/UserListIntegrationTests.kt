package com.adrianraya.technicaltest.ui.list

import app.cash.turbine.test
import com.adrianraya.apptestshared.buildDatabaseUserList
import com.adrianraya.apptestshared.buildRemoteUserList
import com.adrianraya.apptestshared.buildRepositoryWith
import com.adrianraya.technicaltest.data.server.UsersListResult
import com.adrianraya.technicaltest.testrules.CoroutinesTestRule
import com.adrianraya.technicaltest.ui.list.UsersListViewModel.UiState
import com.adrianraya.usecase.userlist.GetUsersListTotalUseCase
import com.adrianraya.usecase.userlist.GetUsersListUseCase
import com.adrianraya.usecase.userlist.RequestUsersListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import com.adrianraya.technicaltest.data.database.UserData as DatabaseUsers

@ExperimentalCoroutinesApi
class UserListIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {
        val remoteData = buildRemoteUserList(2, 3, 4)
        val vm = buildViewModelWith(
            localData = emptyList(),
            remoteData = remoteData
        )

        vm.onUiReady()

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(users = emptyList()), awaitItem())
            assertEquals(
                UiState(
                    users = emptyList(),
                    loading = true
                ),
                awaitItem()
            )
            assertEquals(
                UiState(
                    users = emptyList(),
                    loading = false,
                    totalUsers = "3"
                ),
                awaitItem()
            )

            val user = awaitItem().users!!
            assertEquals("Email 2", user[0].email)
            assertEquals("Email 3", user[1].email)
            assertEquals("Email 4", user[2].email)

            cancel()
        }
    }

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localData = buildDatabaseUserList(1, 2, 3)
        val remoteData = buildRemoteUserList(4, 5, 6)
        val vm = buildViewModelWith(
            localData = localData,
            remoteData = remoteData
        )

        vm.state.test {
            assertEquals(UiState(), awaitItem())

            val user = awaitItem().users!!
            assertEquals("Email 1", user[0].email)
            assertEquals("Email 2", user[1].email)
            assertEquals("Email 3", user[2].email)

            cancel()
        }
    }

    private fun buildViewModelWith(
        localData: List<DatabaseUsers> = emptyList(),
        remoteData: List<UsersListResult.UsersData> = emptyList()
    ): UsersListViewModel {
        val usersRepository = buildRepositoryWith(localData, remoteData)
        val getUsersListUseCase = GetUsersListUseCase(usersRepository)
        val requestUsersListUseCase = RequestUsersListUseCase(usersRepository)
        val getUsersListTotalUseCase = GetUsersListTotalUseCase(usersRepository)
        return UsersListViewModel(getUsersListUseCase, requestUsersListUseCase, getUsersListTotalUseCase)
    }
}

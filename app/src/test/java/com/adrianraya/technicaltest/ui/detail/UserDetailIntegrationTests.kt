package com.adrianraya.technicaltest.ui.detail

import app.cash.turbine.test
import com.adrianraya.apptestshared.buildDatabaseUserList
import com.adrianraya.apptestshared.buildRepositoryWith
import com.adrianraya.technicaltest.data.server.UsersListResult
import com.adrianraya.technicaltest.testrules.CoroutinesTestRule
import com.adrianraya.technicaltest.ui.detail.UserDetailViewModel.UiState
import com.adrianraya.usecase.userdetail.FindUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import com.adrianraya.technicaltest.data.database.UserData as DatabaseUsers

@ExperimentalCoroutinesApi
class UserDetailIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `UI is updated with the comic on start`() = runTest {
        val vm = buildViewModelWith(
            id = 2,
            localData = buildDatabaseUserList(1, 2, 3)
        )

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(2, awaitItem().user!!.id)
            cancel()
        }
    }

    private fun buildViewModelWith(
        id: Int,
        localData: List<DatabaseUsers> = emptyList(),
        remoteData: List<UsersListResult.UsersData> = emptyList()
    ): UserDetailViewModel {
        val usersRepository = buildRepositoryWith(localData, remoteData)
        val findUserUseCase = FindUserUseCase(usersRepository)
        return UserDetailViewModel(id, findUserUseCase)
    }
}

package com.adrianraya.technicaltest.usecase

import com.adrianraya.data.users.UsersRepository
import com.adrianraya.usecase.userlist.GetUsersListTotalUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetUsersTotalUseCaseTest {

    @Test
    fun `Invoke calls users repository`(): Unit = runBlocking {
        val usersRepository = mock<UsersRepository>()
        val getUsersListTotalUseCase = GetUsersListTotalUseCase(usersRepository)

        getUsersListTotalUseCase()

        verify(usersRepository).usersTotal()
    }

    @Test
    fun `Invoke returns correct total number of users`() = runBlocking {
        val usersRepository = mock<UsersRepository>()
        val usersTotal = 55
        whenever(usersRepository.usersTotal()).thenReturn(usersTotal)

        val getUsersListTotalUseCase = GetUsersListTotalUseCase(usersRepository)
        val result = getUsersListTotalUseCase()

        assertEquals(usersTotal, result)
    }

    @Test
    fun `Invoke handles exceptions from repository`() = runBlocking {
        val usersRepository = mock<UsersRepository>()
        val exception = RuntimeException("Error fetching users total")
        whenever(usersRepository.usersTotal()).thenThrow(exception)

        val getUsersListTotalUseCase = GetUsersListTotalUseCase(usersRepository)

        try {
            getUsersListTotalUseCase()
            fail("Exception was expected but not thrown")
        } catch (e: Exception) {
            assertEquals(exception, e)
        }
    }
}

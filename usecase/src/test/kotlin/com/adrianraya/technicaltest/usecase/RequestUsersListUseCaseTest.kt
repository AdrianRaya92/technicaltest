package com.adrianraya.technicaltest.usecase

import com.adrianraya.data.users.UsersRepository
import com.adrianraya.usecase.userlist.RequestUsersListUseCase
import com.adrianraya.domain.Error
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RequestUsersListUseCaseTest {

    @Test
    fun `Invoke calls users repository`(): Unit = runBlocking {
        val usersRepository = mock<UsersRepository>()
        val requestUsersListUseCase = RequestUsersListUseCase(usersRepository)

        requestUsersListUseCase()

        verify(usersRepository).requestUsersList()
    }

    @Test
    fun `Invoke returns correct error response`() = runBlocking {
        val usersRepository = mock<UsersRepository>()
        val errorResponse = Error.Server(500)
        whenever(
            usersRepository.requestUsersList()
        ).thenReturn(errorResponse)

        val requestUsersListUseCase = RequestUsersListUseCase(usersRepository)
        val result = requestUsersListUseCase()

        assertEquals(errorResponse, result)
    }

    @Test
    fun `Invoke handles exceptions from repository`() = runBlocking {
        val usersRepository = mock<UsersRepository>()
        val exception = RuntimeException("Error fetching comics")
        whenever(
            usersRepository.requestUsersList()
        ).thenThrow(exception)

        val requestUsersListUseCase = RequestUsersListUseCase(usersRepository)

        try {
            requestUsersListUseCase()
            fail("Exception was expected but not thrown")
        } catch (e: Exception) {
            assertEquals(exception, e)
        }
    }
}

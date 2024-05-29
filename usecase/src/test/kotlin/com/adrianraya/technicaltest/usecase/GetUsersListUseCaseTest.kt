package com.adrianraya.technicaltest.usecase

import com.adrianraya.data.users.UsersRepository
import com.adrianraya.testshared.sampleUserList
import com.adrianraya.usecase.userlist.GetUsersListUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetUsersListUseCaseTest {

    @Test
    fun `Invoke calls users repository`(): Unit = runBlocking {
        val usersSample = flowOf(listOf(sampleUserList.copy(id = 1)))
        val getUsersListUseCase = GetUsersListUseCase(
            mock {
                on { users } doReturn usersSample
            }
        )

        val result = getUsersListUseCase()

        assertEquals(usersSample, result)
    }

    @Test
    fun `Invoke returns correct users`() = runBlocking {
        val usersRepository = mock<UsersRepository>()
        val sampleUsersList = listOf(sampleUserList.copy(id = 1))
        val userFlow = flowOf(sampleUsersList)
        whenever(usersRepository.users).thenReturn(userFlow)

        val getUsersListUseCase = GetUsersListUseCase(usersRepository)
        val result = getUsersListUseCase().first()

        assertEquals(sampleUsersList, result)
    }

    @Test
    fun `Invoke handles exceptions from repository`() = runBlocking {
        val usersRepository = mock<UsersRepository>()
        val exception = RuntimeException("Error fetching users")
        whenever(usersRepository.users).thenThrow(exception)

        val getUsersListUseCase = GetUsersListUseCase(usersRepository)

        try {
            getUsersListUseCase().collect { }
            fail("Exception was expected but not thrown")
        } catch (e: Exception) {
            assertEquals(exception, e)
        }
    }
}

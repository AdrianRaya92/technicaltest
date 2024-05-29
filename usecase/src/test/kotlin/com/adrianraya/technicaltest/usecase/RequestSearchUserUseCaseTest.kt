package com.adrianraya.technicaltest.usecase

import com.adrianraya.data.users.UsersRepository
import com.adrianraya.domain.Error
import com.adrianraya.domain.repositories.UserDetail
import com.adrianraya.usecase.searchuser.RequestSearchUserUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RequestSearchUserUseCaseTest {

    @Test
    fun `Invoke calls users repository`(): Unit = runBlocking {
        val usersRepository = mock<UsersRepository>()
        val requestSearchUserUseCase = RequestSearchUserUseCase(usersRepository)

        requestSearchUserUseCase(1)

        verify(usersRepository).requestUserDetail(1)
    }

    @Test
    fun `Invoke returns correct error response`() = runBlocking {
        val usersRepository = mock<UsersRepository>()
        val errorResponse = Error.Server(500)
        val userDetailResponse: UserDetail? = null
        whenever(
            usersRepository.requestUserDetail(1)
        ).thenReturn(Pair(errorResponse, userDetailResponse))

        val requestSearchUserUseCase = RequestSearchUserUseCase(usersRepository)
        val result = requestSearchUserUseCase(1)

        assertEquals(Pair(errorResponse, userDetailResponse), result)
    }

    @Test
    fun `Invoke handles exceptions from repository`() = runBlocking {
        val usersRepository = mock<UsersRepository>()
        val exception = RuntimeException("Error fetching comics")
        whenever(
            usersRepository.requestUserDetail(1)
        ).thenThrow(exception)

        val requestSearchUserUseCase = RequestSearchUserUseCase(usersRepository)

        try {
            requestSearchUserUseCase(1)
            fail("Exception was expected but not thrown")
        } catch (e: Exception) {
            assertEquals(exception, e)
        }
    }
}

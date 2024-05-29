package com.adrianraya.technicaltest.usecase

import com.adrianraya.data.users.UsersRepository
import com.adrianraya.testshared.sampleUserList
import com.adrianraya.usecase.userdetail.FindUserUseCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class FindUserUseCaseTest {

    @Test
    fun `Invoke calls users repository`(): Unit = runBlocking {
        val users = flowOf(sampleUserList.copy(id = 1))
        val findUserUseCase = FindUserUseCase(
            mock {
                on { findById(1) } doReturn (users)
            }
        )

        val result = findUserUseCase(1)

        assertEquals(users, result)
    }

    @Test
    fun `Invoke with non-existent user id returns empty flow`() = runBlocking {
        val usersRepository = mock<UsersRepository>()
        whenever(usersRepository.findById(anyInt())).thenReturn(flowOf())

        val findUserUseCase = FindUserUseCase(usersRepository)
        val result = findUserUseCase(999).firstOrNull()

        assertNull(result)
    }

    @Test
    fun `Invoke handles exceptions from repository`() = runBlocking {
        val usersRepository = mock<UsersRepository>()
        val exception = RuntimeException("Error fetching user")
        whenever(usersRepository.findById(anyInt())).thenThrow(exception)

        val findUserUseCase = FindUserUseCase(usersRepository)

        try {
            findUserUseCase(1).collect { }
            Assert.fail("Exception was expected but not thrown")
        } catch (e: Exception) {
            assertEquals(exception, e)
        }
    }
}

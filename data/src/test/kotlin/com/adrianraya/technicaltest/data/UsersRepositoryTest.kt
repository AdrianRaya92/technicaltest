package com.adrianraya.technicaltest.data

import arrow.core.left
import arrow.core.right
import com.adrianraya.data.users.UsersRepository
import com.adrianraya.data.users.datasource.UsersLocalDataSource
import com.adrianraya.data.users.datasource.UsersRemoteDataSource
import com.adrianraya.domain.Error
import com.adrianraya.testshared.sampleUserList
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class UsersRepositoryTest {

    @Mock
    lateinit var localDataSource: UsersLocalDataSource

    @Mock
    lateinit var remoteDataSource: UsersRemoteDataSource

    private lateinit var usersRepository: UsersRepository

    private val localComics = flowOf(listOf(sampleUserList.copy(1)))

    @Before
    fun setUp() {
        whenever(localDataSource.users).thenReturn(localComics)
        usersRepository = UsersRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `Users are taken from local data source if available`(): Unit = runBlocking {
        val result = usersRepository.users

        assertEquals(localComics, result)
    }

    @Test
    fun `Users are saved to local data source when it's empty`(): Unit = runBlocking {
        val remoteUsers = listOf(sampleUserList.copy(2))
        whenever(localDataSource.isEmpty()).thenReturn(true)
        whenever(remoteDataSource.getUsersList()).thenReturn(remoteUsers.right())

        usersRepository.requestUsersList()

        verify(localDataSource).save(remoteUsers)
    }

    @Test
    fun `Finding a user by id is done in local data source`(): Unit = runBlocking {
        val user = flowOf(sampleUserList.copy(id = 5))
        whenever(localDataSource.findById(5)).thenReturn(user)

        val result = usersRepository.findById(5)

        assertEquals(user, result)
    }

    @Test
    fun `UsersTotal returns correct total number of user`(): Unit = runBlocking {
        val totalUser = 40
        whenever(localDataSource.getUsersTotal()).thenReturn(totalUser)

        val result = usersRepository.usersTotal()

        assertEquals(totalUser, result)
    }

    @Test
    fun `RequestUsersList handles errors from remote data source`(): Unit = runBlocking {
        val errorResponse = Error.Server(500)
        whenever(localDataSource.isEmpty()).thenReturn(true)
        whenever(remoteDataSource.getUsersList()).thenReturn(errorResponse.left())

        val result = usersRepository.requestUsersList()

        assertEquals(errorResponse, result)
    }

    @Test
    fun `RequestUsersList does not call remote data source when not needed`(): Unit = runBlocking {
        whenever(localDataSource.isEmpty()).thenReturn(false)

        usersRepository.requestUsersList()

        verify(remoteDataSource, never()).getUsersList()
    }
}

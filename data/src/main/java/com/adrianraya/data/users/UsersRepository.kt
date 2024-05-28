package com.adrianraya.data.users

import com.adrianraya.data.users.datasource.UsersLocalDataSource
import com.adrianraya.data.users.datasource.UsersRemoteDataSource
import com.adrianraya.domain.Error
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val localDataSource: UsersLocalDataSource,
    private val remoteDataSource: UsersRemoteDataSource
) {
    val users get() = localDataSource.users

    suspend fun usersTotal(): Int = localDataSource.getUsersTotal()

    suspend fun requestUsersList(): Error? {
        val findComics = when {
            localDataSource.isEmpty() -> true
            else -> false
        }

        if (findComics) {
            val users = remoteDataSource.getUsersList()
            return users.fold(ifLeft = { it }) {
                localDataSource.save(it)
                null
            }
        }
        return null
    }
}

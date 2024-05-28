package com.adrianraya.data.users.datasource

import com.adrianraya.domain.repositories.Users
import com.adrianraya.domain.Error
import kotlinx.coroutines.flow.Flow

interface UsersLocalDataSource {
    val users: Flow<List<Users>>

    suspend fun isEmpty(): Boolean
    suspend fun getUsersTotal(): Int
    fun findById(id: Int): Flow<Users>
    suspend fun save(users: List<Users>): Error?
}

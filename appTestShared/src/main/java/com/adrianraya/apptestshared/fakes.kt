package com.adrianraya.apptestshared

import com.adrianraya.technicaltest.data.database.UserData
import com.adrianraya.technicaltest.data.database.UsersDao
import com.adrianraya.technicaltest.data.server.UserAPI
import com.adrianraya.technicaltest.data.server.UserDetailResult
import com.adrianraya.technicaltest.data.server.UsersListResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import com.adrianraya.technicaltest.data.database.UserData as DatabaseUsers

class FakeUsersDao(users: List<DatabaseUsers> = emptyList()) : UsersDao {

    private val inMemoryComics = MutableStateFlow(users)
    private lateinit var findUsersFlow: MutableStateFlow<DatabaseUsers>

    override fun getAll(): Flow<List<DatabaseUsers>> = inMemoryComics

    override fun findById(id: Int): Flow<DatabaseUsers> {
        findUsersFlow = MutableStateFlow(inMemoryComics.value.first { it.id == id })
        return findUsersFlow
    }

    override suspend fun usersCount(): Int = inMemoryComics.value.size

    override suspend fun insertUsers(users: List<UserData>) {
        inMemoryComics.value = users

        if (::findUsersFlow.isInitialized) {
            users.firstOrNull { it.id == findUsersFlow.value.id }
                ?.let { findUsersFlow.value = it }
        }
    }
}

class FakeUsersListRemoteService(private val users: List<UsersListResult.UsersData> = emptyList()) : UserAPI {

    override suspend fun getUsersList(perPage: Int) = UsersListResult(
        1,
        perPage,
        1,
        1,
        users
    )

    override suspend fun getUserDetail(userId: Int) = UserDetailResult(
        UserDetailResult.UserDetailData(
            1,
            "email@example",
            "Adrian",
            "Raya",
            "avatar"
        )
    )
}

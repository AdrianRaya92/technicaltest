package com.adrianraya.technicaltest.data.database

import com.adrianraya.data.users.datasource.UsersLocalDataSource
import com.adrianraya.domain.repositories.Users
import com.adrianraya.domain.Error
import com.adrianraya.technicaltest.data.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.adrianraya.technicaltest.data.database.UserData as DbUsers

class UsersRoomDataSource @Inject constructor(private val usersDao: UsersDao) :
    UsersLocalDataSource {

    override val users: Flow<List<Users>> = usersDao.getAll().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = usersDao.usersCount() == 0

    override suspend fun getUsersTotal(): Int = usersDao.usersCount()

    override fun findById(id: Int): Flow<Users> = usersDao.findById(id).map { it.toDomainModel() }

    override suspend fun save(users: List<Users>): Error? = tryCall {
        usersDao.insertUsers(users.fromDomainModel())
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )
}

private fun List<DbUsers>.toDomainModel(): List<Users> = map { it.toDomainModel() }

private fun DbUsers.toDomainModel(): Users =
    Users(
        id, email, first_name, last_name, avatar
    )

private fun List<Users>.fromDomainModel(): List<DbUsers> = map { it.fromDomainModel() }

private fun Users.fromDomainModel(): DbUsers = DbUsers(
    id, email, first_name, last_name, avatar
)

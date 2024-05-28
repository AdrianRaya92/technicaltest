package com.adrianraya.technicaltest.data.server

import arrow.core.Either
import com.adrianraya.data.users.datasource.UsersRemoteDataSource
import com.adrianraya.domain.UserApiData
import com.adrianraya.domain.repositories.Users
import com.adrianraya.technicaltest.data.tryCall
import com.adrianraya.domain.Error
import javax.inject.Inject

class RemoteDatasource@Inject constructor(
    private val userApi: UserAPI,
) : UsersRemoteDataSource {

    override suspend fun getUsersList(): Either<Error, List<Users>> = tryCall {
        userApi
            .getUsersList(UserApiData.limit)
            .data
            .toDomainModel()
    }

    private fun List<UsersListResult.UsersData>.toDomainModel(): List<Users> =
        map { it.toDomainModel() }

    private fun UsersListResult.UsersData.toDomainModel(): Users =
        Users(
            id,
            email,
            first_name,
            last_name,
            avatar)
}
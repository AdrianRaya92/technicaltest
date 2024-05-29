package com.adrianraya.data.users.datasource

import arrow.core.Either
import com.adrianraya.domain.Error
import com.adrianraya.domain.repositories.UserDetail
import com.adrianraya.domain.repositories.Users

interface UsersRemoteDataSource {
    suspend fun getUsersList(): Either<Error, List<Users>>
    suspend fun getUserDetail(id: Int): Either<Error, UserDetail>
}

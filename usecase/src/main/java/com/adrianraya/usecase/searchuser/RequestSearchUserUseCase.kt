package com.adrianraya.usecase.searchuser

import com.adrianraya.data.users.UsersRepository
import com.adrianraya.domain.Error
import com.adrianraya.domain.repositories.UserDetail
import javax.inject.Inject

class RequestSearchUserUseCase @Inject constructor(private val repository: UsersRepository) {

    suspend operator fun invoke(userId: Int): Pair<Error?, UserDetail?>{
        return repository.requestUserDetail(userId)
    }
}

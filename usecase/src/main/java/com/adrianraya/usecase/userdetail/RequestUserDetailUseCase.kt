package com.adrianraya.usecase.userdetail

import com.adrianraya.data.users.UsersRepository
import com.adrianraya.domain.Error
import javax.inject.Inject

class RequestUserDetailUseCase @Inject constructor(private val repository: UsersRepository) {

    suspend operator fun invoke(userId: Int): Error? {
        return repository.requestUserDetail(userId)
    }
}

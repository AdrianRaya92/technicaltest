package com.adrianraya.usecase.userdetail

import com.adrianraya.data.users.UsersRepository
import com.adrianraya.domain.repositories.Users
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindUserUseCase @Inject constructor(private val repository: UsersRepository) {

    operator fun invoke(id: Int): Flow<Users> = repository.findById(id)
}

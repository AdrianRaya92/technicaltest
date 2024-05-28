package com.adrianraya.usecase.userlist

import com.adrianraya.data.users.UsersRepository
import com.adrianraya.domain.Error
import javax.inject.Inject

class RequestUsersListUseCase @Inject constructor(private val repository: UsersRepository) {

    suspend operator fun invoke(): Error? {
        return repository.requestUsersList()
    }
}

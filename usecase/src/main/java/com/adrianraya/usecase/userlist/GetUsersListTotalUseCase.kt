package com.adrianraya.usecase.userlist

import com.adrianraya.data.users.UsersRepository
import javax.inject.Inject

class GetUsersListTotalUseCase @Inject constructor(private val repository: UsersRepository) {

    suspend operator fun invoke(): Int {
        return repository.usersTotal()
    }
}

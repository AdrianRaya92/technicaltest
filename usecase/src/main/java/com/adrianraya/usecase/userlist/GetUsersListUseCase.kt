package com.adrianraya.usecase.userlist

import com.adrianraya.data.users.UsersRepository
import javax.inject.Inject

class GetUsersListUseCase @Inject constructor(private val repository: UsersRepository) {

    operator fun invoke() = repository.users
}

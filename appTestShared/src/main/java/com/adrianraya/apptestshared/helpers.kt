package com.adrianraya.apptestshared

import com.adrianraya.data.users.UsersRepository
import com.adrianraya.technicaltest.data.database.UsersRoomDataSource
import com.adrianraya.technicaltest.data.server.RemoteDatasource
import com.adrianraya.technicaltest.data.server.UsersListResult
import com.adrianraya.technicaltest.data.database.UserData as DatabaseUsers

fun buildRepositoryWith(
    localData: List<DatabaseUsers>,
    remoteData: List<UsersListResult.UsersData>
): UsersRepository {
    val localDataSource = UsersRoomDataSource(FakeUsersDao(localData))
    val remoteDataSource =
        RemoteDatasource(FakeUsersListRemoteService(remoteData))
    return UsersRepository(localDataSource, remoteDataSource)
}

fun buildDatabaseUserList(vararg id: Int) = id.map {
    DatabaseUsers(
        id = it,
        avatar = "Avatar $it",
        email = "Email $it",
        first_name = "First name $it",
        last_name = "Last name $it"
    )
}

fun buildRemoteUserList(vararg id: Int) = id.map {
    UsersListResult.UsersData(
        id = it,
        avatar = "Avatar $it",
        email = "Email $it",
        first_name = "First name $it",
        last_name = "Last name $it"
    )
}
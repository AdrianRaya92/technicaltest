package com.adrianraya.technicaltest.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Query("SELECT * FROM UserData ORDER BY first_name ASC")
    fun getAll(): Flow<List<UserData>>

    @Query("SELECT * FROM UserData WHERE id = :id")
    fun findById(id: Int): Flow<UserData>

    @Query("SELECT COUNT(id) FROM UserData")
    suspend fun usersCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserData>)
}

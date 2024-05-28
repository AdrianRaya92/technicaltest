package com.adrianraya.technicaltest.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
)

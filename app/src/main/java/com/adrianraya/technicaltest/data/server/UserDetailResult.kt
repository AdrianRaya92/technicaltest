package com.adrianraya.technicaltest.data.server

data class UserDetailResult(
    val data: UserDetailData
){
    data class UserDetailData(
        val id: Int,
        val email: String,
        val first_name: String,
        val last_name: String,
        val avatar: String)
}

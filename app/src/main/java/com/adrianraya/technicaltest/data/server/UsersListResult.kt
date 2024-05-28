package com.adrianraya.technicaltest.data.server

data class UsersListResult(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<UsersData>,
) {

    data class UsersData(
        val id: Int,
        val email: String,
        val first_name: String,
        val last_name: String,
        val avatar: String
    )
}

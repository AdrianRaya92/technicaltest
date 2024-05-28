package com.adrianraya.technicaltest.data.server

import retrofit2.http.*

interface UserAPI {

    @Headers("Content-Type: application/json; charset=UTF-8")
    @GET("users")
    suspend fun getUsersList(
        @Query("per_page") perPage: Int,
    ): UsersListResult
}

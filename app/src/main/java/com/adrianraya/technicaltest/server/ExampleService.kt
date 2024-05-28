package com.adrianraya.technicaltest.server

import retrofit2.http.GET
import retrofit2.http.Query

interface ExampleService {

    @GET("v1/public/comics")
    suspend fun listMarvelComics(
        @Query("apikey") apiKey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("dateRange") dateRange: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): RemoteResult
}

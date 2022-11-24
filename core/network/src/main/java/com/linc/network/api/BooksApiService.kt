package com.linc.network.api

import com.linc.network.model.BaseBooksResponse
import retrofit2.http.GET

interface BooksApiService {

    @GET("/1.0/new")
    suspend fun getNewBooks(): BaseBooksResponse

}
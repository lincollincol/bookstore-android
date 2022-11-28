package com.linc.network.api

import com.linc.network.model.BaseBooksResponse
import com.linc.network.model.BaseResponse
import com.linc.network.model.book.BookApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApiService {

//    @GET("/1.0/new")
//    suspend fun getNewBooks(): BaseBooksResponse

    //https://developers.google.com/books/docs/v1/using

    @GET("/v1/volumes")
    suspend fun getNewBooks(
        @Query("q") query: String = "fantasy",
        @Query("maxResults") maxResults: Int = 3,
        @Query("printType") printType: String = "books",
        @Query("key") key: String = "AIzaSyCa-Wb313sPl3192qPVn5cyAE9b4GXcgEE",
    ): BaseResponse<BookApiModel>

}
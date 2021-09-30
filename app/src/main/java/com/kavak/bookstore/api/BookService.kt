package com.kavak.bookstore.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BookService {

    @GET("books.json")
    suspend fun getBooks(): Response<BookResponse>

    @GET("best_sellers.json")
    suspend fun getBestSellers(): Response<BestSellerResponse>

    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com/ejgteja/files/main/"

        fun create(): BookService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BookService::class.java)
        }
    }
}
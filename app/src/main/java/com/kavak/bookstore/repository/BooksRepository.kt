package com.kavak.bookstore.repository

import com.kavak.bookstore.api.BookService
import javax.inject.Inject

class BooksRepository @Inject constructor(private val service: BookService) {

    suspend fun getBooks() = service.getBooks()

    suspend fun getBestSellers() = service.getBestSellers()
}
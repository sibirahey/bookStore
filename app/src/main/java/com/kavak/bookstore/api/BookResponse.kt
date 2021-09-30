package com.kavak.bookstore.api

data class BookResponse(
    val results: ResultsBooks
)

class ResultsBooks(
    val books: List<BooksApi>
)

class BooksApi(
    val isbn: String,
    val title: String,
    val author: String,
    val genre: String,
    val img: String
)

data class BestSellerResponse(
    val results: ResultsBestSellers
)

class ResultsBestSellers(
    val best_sellers: List<String>
)

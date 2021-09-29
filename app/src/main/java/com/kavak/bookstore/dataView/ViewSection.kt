package com.kavak.bookstore.dataView

sealed class ViewSection(open val id: String)

class BookSectionTitle(
    override val id: String,
    val sectionTitle: String,
    val quantity: Int
) : ViewSection(id)

class BookTransversalSection(
    override val id: String,
    val books: List<BookItem>
) : ViewSection(id)

class BookItem(
    override val id: String,
    val coverImage: String,
    val title: String,
    val author: String
) : ViewSection(id)

package com.kavak.bookstore.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavak.bookstore.api.BestSellerResponse
import com.kavak.bookstore.api.BookResponse
import com.kavak.bookstore.api.BooksApi
import com.kavak.bookstore.dataView.*
import com.kavak.bookstore.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: BooksRepository) : ViewModel() {

    private val mutableScreenStateLiveData: MutableLiveData<ScreenState> =
        MutableLiveData(LoadingState)
    val screenStateLiveData: LiveData<ScreenState> = mutableScreenStateLiveData

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        mutableScreenStateLiveData.postValue(ErrorState)
    }


    fun getBooks() {
        viewModelScope.launch(exceptionHandler) {
            val deferredBooks = async(Dispatchers.IO) { repository.getBooks() }
            val deferredBestSellers = async(Dispatchers.IO) { repository.getBestSellers() }
            val booksResponse = deferredBooks.await()
            val bestSellersResponse = deferredBestSellers.await()

            val books = booksResponse.body()?.results?.books

            if (!booksResponse.isSuccessful || books.isNullOrEmpty()) {
                mutableScreenStateLiveData.postValue(ErrorState)
                return@launch
            }

            val bestSellers = bestSellersResponse.body()?.results?.best_sellers
            if (!bestSellersResponse.isSuccessful || bestSellers.isNullOrEmpty()) {
                mutableScreenStateLiveData.postValue(NoBestSellersState)
            }

            val mockSections = getBooksSections(books, bestSellers)
            mutableScreenStateLiveData.postValue(CompleteState(mockSections))
        }

    }

    private fun getBooksSections(
        books: List<BooksApi>,
        bestSellers: List<String>?
    ): List<ViewSection> {
        val list = mutableListOf<ViewSection>()
        val transversalList = mutableListOf<BookItem>()

        //calculate best sellers
        if (bestSellers != null && bestSellers.isNotEmpty()) {
            bestSellers.forEach { bestSellerISBN ->
                books.firstOrNull { it.isbn == bestSellerISBN }?.let {
                    transversalList.add(it.toBookItemView())
                }
            }
        }
        if (transversalList.isNotEmpty()) {
            list.add(BookSectionTitle(BEST_SELLERS_ID, BEST_SELLERS_TITLE, transversalList.size))
            list.add(BookTransversalSection("transversal", transversalList))
        }

        //group books by section
        books.groupBy { it.genre }.forEach { (section, booksOnSection) ->
            list.add(BookSectionTitle(section, section, booksOnSection.size))
            booksOnSection.forEach {
                list.add(it.toBookItemView())
            }
        }
        return list
    }

    companion object {
        const val BEST_SELLERS_TITLE: String = "BEST SELLERS"
        const val BEST_SELLERS_ID: String = "best_sellers"
    }
}

private fun BooksApi.toBookItemView(): BookItem {
    return BookItem(isbn, img, title, author)
}

package com.kavak.bookstore.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavak.bookstore.dataView.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val mutableScreenStateLiveData: MutableLiveData<ScreenState> =
        MutableLiveData(LoadingState)
    val screenStateLiveData: LiveData<ScreenState> = mutableScreenStateLiveData


    fun getBooks() {
        viewModelScope.launch {
            val mockSections = getMockSections()
            mutableScreenStateLiveData.postValue(CompleteState(mockSections))
        }

    }

    private fun getMockSections(): List<ViewSection> {
        val list = mutableListOf<ViewSection>()
        val book1 = BookItem("001", "https://raw.githubusercontent.com/ejgteja/files/main/0735223793.jpg", "Comedy1", "jaja")
        val book2 = BookItem("002", "https://raw.githubusercontent.com/ejgteja/files/main/0735211299.jpg", "Comede2", "haha")
        val transversalList = mutableListOf<BookItem>()
        transversalList.add(book1)
        transversalList.add(book2)


        list.add(BookSectionTitle("best_sellers", "best_sellers", 2))
        list.add(BookTransversalSection("transversal", transversalList))
        list.add(BookSectionTitle("Comedy", "Comedy", 2))
        list.add(book1)
        list.add(book2)
        list.add(book1)
        list.add(book2)
        list.add(book1)
        list.add(book2)
        list.add(book1)
        list.add(book2)
        list.add(book1)
        list.add(book2)
        list.add(book1)
        list.add(book2)
        list.add(book1)
        list.add(book2)
        list.add(book1)
        list.add(book2)
        return list
    }
}
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
        list.add(BookSectionTitle("Comedy", "Comedy", 2))
        list.add(BookItem("001", "", "Comedy1", "jaja"))
        list.add(BookItem("002", "", "Comede2", "haha"))
        return list
    }
}
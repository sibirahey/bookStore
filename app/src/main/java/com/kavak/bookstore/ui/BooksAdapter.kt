package com.kavak.bookstore.ui


import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kavak.bookstore.dataView.*

class BooksAdapter() : ListAdapter<ViewSection, ViewHolder>(ViewBookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is BookSectionTitle -> BOOK_SECTION_TITLE
            is BookTransversalSection -> BOOK_TRANSVERSAL_SECTION
            is BookItem -> BOOK_ITEM
        }
    }

    companion object {
        const val BOOK_SECTION_TITLE = 0
        const val BOOK_TRANSVERSAL_SECTION = 1
        const val BOOK_ITEM = 2
    }

}

class ViewBookDiffCallback : DiffUtil.ItemCallback<ViewSection>() {
    override fun areItemsTheSame(
        oldItem: ViewSection,
        newItem: ViewSection
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ViewSection,
        newItem: ViewSection
    ): Boolean {
        return oldItem == newItem
    }

}
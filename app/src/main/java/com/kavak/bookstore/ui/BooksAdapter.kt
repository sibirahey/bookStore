package com.kavak.bookstore.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.kavak.bookstore.R
import com.kavak.bookstore.dataView.*
import com.kavak.bookstore.databinding.BookItemLayoutBinding
import com.kavak.bookstore.databinding.BookSectionTitleLayoutBinding
import com.kavak.bookstore.databinding.BookTransversalSectionLayoutBinding

class BooksAdapter() : ListAdapter<ViewSection, ViewHolder>(ViewBookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            BOOK_SECTION_TITLE -> {
                val viewBinding = BookSectionTitleLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                BookSectionTitleViewHolder(viewBinding)
            }
            BOOK_TRANSVERSAL_SECTION
            -> {
                val viewBinding = BookTransversalSectionLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                BookTransversalSectionViewHolder(viewBinding)
            }
            BOOK_ITEM
            -> {
                val viewBinding = BookItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                BookItemViewHolder(viewBinding)
            }
            else -> DefaultViewHolder(TextView(parent.context))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is BookSectionTitleViewHolder -> {
                holder.bind(getItem(position) as BookSectionTitle)
            }
            is BookTransversalSectionViewHolder -> {
                holder.bind(getItem(position) as BookTransversalSection)
            }
            is BookItemViewHolder -> {
                holder.bind(getItem(position) as BookItem)
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is BookSectionTitle -> BOOK_SECTION_TITLE
            is BookTransversalSection -> BOOK_TRANSVERSAL_SECTION
            is BookItem -> BOOK_ITEM
        }
    }

    class BookSectionTitleViewHolder(private val viewBinding: BookSectionTitleLayoutBinding) :
        ViewHolder(viewBinding.root) {

        fun bind(bookSectionTitle: BookSectionTitle) {
            viewBinding.apply {
                sectionTitle.text = bookSectionTitle.sectionTitle
                sectionQuantity.text = bookSectionTitle.quantity.toString()
            }
        }
    }

    class BookTransversalSectionViewHolder(private val viewBinding: BookTransversalSectionLayoutBinding) :
        ViewHolder(viewBinding.root) {

        fun bind(bookTransversalSection: BookTransversalSection) {
            if (bookTransversalSection.books.isNotEmpty()) {
                val adapter = BooksAdapter()
                viewBinding.recycler.adapter = adapter
                adapter.submitList(bookTransversalSection.books)
            }
        }
    }

    class BookItemViewHolder(private val viewBinding: BookItemLayoutBinding) :
        ViewHolder(viewBinding.root) {

        fun bind(item: BookItem) {
            viewBinding.apply {
                bookTitle.text = item.title
                bookAuthor.text = item.author
            }
            if (item.coverImage.isNotEmpty()) {
                viewBinding.bookImage.visibility = View.VISIBLE
                Glide.with(viewBinding.bookImage.context)
                    .load(item.coverImage)
                    .error(R.drawable.ic_book)
                    .into(viewBinding.bookImage)
            }
        }
    }

    class DefaultViewHolder(itemView: View) : ViewHolder(itemView)

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
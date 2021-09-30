package com.kavak.bookstore.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kavak.bookstore.R
import com.kavak.bookstore.dataView.*
import com.kavak.bookstore.databinding.MainFragmentBinding
import com.kavak.bookstore.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var bookAdapter: ListAdapter<ViewSection, RecyclerView.ViewHolder>
    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        setupView()
        setupObservers()
        return binding.root
    }

    private fun setupView() {
        bookAdapter = BooksAdapter()
        binding.apply {
            recycler.adapter = bookAdapter
            swipeRefresh.setOnRefreshListener { viewModel.getBooks() }
        }
    }

    private fun setupObservers() {
        viewModel.screenStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is CompleteState -> displayCompleteState(it)
                LoadingState -> displayLoadingState()
                NoBestSellersState -> {
                    Toast.makeText(context, getString(R.string.no_best_sellers), Toast.LENGTH_SHORT)
                        .show()
                }
                ErrorState -> displayErrorState()
                else -> displayLoadingState()
            }
        }
        viewModel.getBooks()
    }

    private fun displayErrorState() {
        binding.apply {
            progressCircular.visibility = View.GONE
            swipeRefresh.isRefreshing = false
            errorGroup.visibility = View.VISIBLE
        }
    }

    private fun displayCompleteState(it: CompleteState) {
        bookAdapter.submitList(it.sections)
        binding.apply {
            progressCircular.visibility = View.GONE
            errorGroup.visibility = View.GONE
            swipeRefresh.isRefreshing = false
        }
    }

    private fun displayLoadingState() {
        binding.apply {
            progressCircular.visibility = View.VISIBLE
            errorGroup.visibility = View.GONE
        }
    }

}
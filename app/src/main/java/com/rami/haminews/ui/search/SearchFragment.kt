package com.rami.haminews.ui.search

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rami.haminews.R
import com.rami.haminews.databinding.FragmentSearchBinding
import com.rami.haminews.ui.adapters.NewsAdapter
import com.rami.haminews.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding: FragmentSearchBinding by viewBinding()
    private val viewModel by viewModels<SearchViewModel>()
    lateinit var newsAdapter: NewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObserver()
    }

    private fun initObserver() {
        var job: Job? = null
        binding.searchEd.addTextChangedListener { text: Editable? ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                text?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.getSearchNews(query = it.toString())
                    }
                }
            }
        }
        viewModel.searchNewsLiveData.observe(viewLifecycleOwner) { responce ->
            when (responce) {
                is Resource.Success -> {
                    binding.pagSearchProgressBar.visibility = View.INVISIBLE
                    responce.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }

                is Resource.Error -> {
                    binding.pagSearchProgressBar.visibility = View.INVISIBLE
                    responce.data?.let {
                        Log.e("COK", "MainFragment: error: $it")
                    }
                }

                is Resource.Loading -> {
                    binding.pagSearchProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        binding.searchNewsAdapterRv.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}

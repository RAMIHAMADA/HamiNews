package com.rami.haminews.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rami.haminews.R
import com.rami.haminews.databinding.FragmentMainBinding
import com.rami.haminews.ui.adapters.NewsAdapter
import com.rami.haminews.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding: FragmentMainBinding by viewBinding()
    private val viewModel by viewModels<MainViewModel>()
    lateinit var newsAdapter: NewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setOnItemClickListener()
        initObserver()
    }

    private fun initObserver() {
        viewModel.newsLiveData.observe(viewLifecycleOwner) { responce ->
            when (responce) {
                is Resource.Success -> {
                    binding.pagProgressBar.visibility = View.INVISIBLE
                    responce.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }

                is Resource.Error -> {
                    binding.pagProgressBar.visibility = View.INVISIBLE
                    responce.data?.let {
                        Log.e("COK", "MainFragment: error: $it")
                    }
                }

                is Resource.Loading -> {
                    binding.pagProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        binding.newsAdapterRv.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setOnItemClickListener() {
        newsAdapter.setOnItemClickListener {
            val bundle = bundleOf("article" to it)
            view?.findNavController()?.navigate(
                R.id.action_mainFragment_to_detailsFragment,
                bundle,
            )
        }
    }
}

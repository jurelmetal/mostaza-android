package com.juanetoh.mostaza.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.juanetoh.mostaza.R
import com.juanetoh.mostaza.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModels()
    private lateinit var viewBinding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentMainBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = PostListAdapter()

        viewBinding.refreshLayout.setOnRefreshListener {
            viewModel.getPosts()
        }

        viewBinding.fab.setOnClickListener {
            findNavController().navigate(R.id.newPostFragment)
        }

        viewBinding.toggleSort.setOnClickListener {
            viewModel.toggleSort()
        }

        viewBinding.postsList.adapter = adapter
        viewModel.sortedPostList.observe(viewLifecycleOwner) {
            adapter.submitList(it) {
                viewBinding.postsList.scrollToPosition(0)
            }
            viewBinding.refreshLayout.isRefreshing = false
        }
        viewModel.getPosts()
    }
}
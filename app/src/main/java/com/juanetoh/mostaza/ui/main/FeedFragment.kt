package com.juanetoh.mostaza.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
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
        val adapter = PostListAdapter {
            findNavController().navigate(
                R.id.action_feed_to_postdetail,
                bundleOf(
                    PostDetailFragment.EXTRA_ARG_POST_DATA to it
                )
            )
        }

        viewBinding.refreshLayout.setOnRefreshListener {
            viewModel.getPosts()
        }

        viewBinding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feed_to_newpost)
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

        viewModel.errorToast.observe(viewLifecycleOwner) {
            val message = getString(it.resId)
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        viewModel.getPosts()
    }

    companion object {
        const val FEED_MAX_CONTENT_CHARS = 30
    }
}
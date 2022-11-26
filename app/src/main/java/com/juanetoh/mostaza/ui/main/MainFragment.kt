package com.juanetoh.mostaza.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.juanetoh.mostaza.R
import com.juanetoh.mostaza.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var viewBinding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentMainBinding.inflate(inflater)
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
            adapter.submitList(it)
            viewBinding.refreshLayout.isRefreshing = false
        }
        viewModel.getPosts()
    }
}
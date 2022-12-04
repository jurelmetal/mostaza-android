package com.juanetoh.mostaza.ui.main

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.juanetoh.mostaza.R
import com.juanetoh.mostaza.api.model.Post
import com.juanetoh.mostaza.databinding.FragmentPostDetailBinding
import com.juanetoh.mostaza.extensions.humanReadableDelta
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailFragment : Fragment() {

    private lateinit var viewModel: PostDetailViewModel
    private lateinit var viewBinding: FragmentPostDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PostDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentPostDetailBinding.inflate(inflater)
        return viewBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.pageState.observe(viewLifecycleOwner) {
            when(it) {
                is PostDetailViewModel.ReadyState.Ready -> {
                    viewBinding.postdetailAuthor.text = it.post.displayName
                    viewBinding.postdetailContent.text = it.post.content
                    viewBinding.postDate2.text = it.post.creationDate.humanReadableDelta(requireContext())
                }
                is PostDetailViewModel.ReadyState.Loading -> {
                    viewBinding.postdetailContent.text = getString(R.string.post_detail_loading)
                }
                is PostDetailViewModel.ReadyState.Error -> {
                    viewBinding.postdetailContent.text = getString(R.string.post_detail_error, it.message)
                }
            }
        }

        val post = arguments?.getParcelable(EXTRA_ARG_POST_DATA, Post::class.java)
        viewModel.loadPost(post)
    }

    companion object {
        const val EXTRA_ARG_POST_DATA = "postData"
    }
}
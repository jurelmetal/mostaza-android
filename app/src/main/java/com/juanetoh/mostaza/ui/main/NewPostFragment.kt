package com.juanetoh.mostaza.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.juanetoh.mostaza.databinding.FragmentNewPostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPostFragment : Fragment() {

    private lateinit var viewModel: NewPostViewModel
    private lateinit var viewBinding: FragmentNewPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewPostViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentNewPostBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        retrieveCurrentAuthor()
        setupViewListeners()
        setupViewModelObservers()
    }

    private fun retrieveCurrentAuthor() {
        val author = viewModel.getAuthor()
        viewBinding.authorInput.setText(author, TextView.BufferType.EDITABLE)
    }

    private fun setupViewModelObservers() {
        viewModel.toastMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.dismissFragment.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
            }
        }
    }

    private fun setupViewListeners() {
        viewBinding.contentEdittext.addTextChangedListener {
            it?.let { viewModel.onContentTextUpdated(it.toString()) }
        }

        viewBinding.buttonPost.setOnClickListener {
            viewModel.doPost()
        }

        viewBinding.authorInput.addTextChangedListener {
            it?.let { viewModel.onAuthorNameUpdated(it.toString()) }
        }
    }
}
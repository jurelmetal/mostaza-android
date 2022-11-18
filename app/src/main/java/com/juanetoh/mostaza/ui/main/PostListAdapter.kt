package com.juanetoh.mostaza.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.juanetoh.mostaza.api.model.Post
import com.juanetoh.mostaza.databinding.PostBinding

class PostViewHolder(val binding: PostBinding) : ViewHolder(binding.root) {
    fun apply(post: Post) {
        binding.labelAuthor.text = post.authorId
        binding.labelContent.text = post.content
    }
}

object postDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.content == newItem.content &&
                oldItem.authorId == newItem.authorId
    }
}

class PostListAdapter : ListAdapter<Post, PostViewHolder>(postDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(PostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.apply(getItem(position))
    }
}
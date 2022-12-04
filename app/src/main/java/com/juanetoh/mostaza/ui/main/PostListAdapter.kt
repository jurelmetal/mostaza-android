package com.juanetoh.mostaza.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.juanetoh.mostaza.api.model.Post
import com.juanetoh.mostaza.databinding.PostBinding
import com.juanetoh.mostaza.extensions.humanReadableDelta

class PostViewHolder(private val binding: PostBinding) : ViewHolder(binding.root) {
    fun apply(post: Post, onItemClicked: (Post) -> Unit) {
        binding.labelAuthor.text = post.displayName
        binding.labelContent.text = post.content.take(FeedFragment.FEED_MAX_CONTENT_CHARS)
        binding.postDate.text = post.creationDate.humanReadableDelta(binding.root.context)
        binding.root.setOnClickListener { onItemClicked(post) }
    }
}

object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.content == newItem.content &&
                oldItem.authorId == newItem.authorId
    }
}

class PostListAdapter(private val onItemClicked: (Post) -> Unit) : ListAdapter<Post, PostViewHolder>(PostDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(PostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.apply(getItem(position), onItemClicked)
    }
}
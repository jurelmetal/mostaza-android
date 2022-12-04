package com.juanetoh.mostaza.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanetoh.mostaza.api.model.Post
import com.juanetoh.mostaza.extensions.readOnly
import kotlinx.coroutines.launch

class PostDetailViewModel : ViewModel() {
    open class ReadyState {
        object Loading : ReadyState()
        class Error(val message: String) : ReadyState()
        class Ready(val post: Post) : ReadyState()
    }
    private val _readyState = MutableLiveData<ReadyState>(ReadyState.Loading)
    val pageState = _readyState.readOnly()

    fun loadPost(post: Post?) {
        viewModelScope.launch {
            post?.let {
                _readyState.value = ReadyState.Ready(post)
            } ?: run {
                _readyState.value = ReadyState.Error("Post is null")
            }
        }
    }
}
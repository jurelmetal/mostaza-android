package com.juanetoh.mostaza.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanetoh.mostaza.api.model.ApiClient
import com.juanetoh.mostaza.api.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val apiClient: ApiClient
) : ViewModel() {
    private val _postList: MutableLiveData<List<Post>> = MutableLiveData()
    val postList: LiveData<List<Post>> = _postList

    fun getPosts() {
        viewModelScope.launch {
            val posts = apiClient.getPosts()
            _postList.postValue(posts)
        }
    }
}
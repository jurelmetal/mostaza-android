package com.juanetoh.mostaza.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanetoh.mostaza.MainActivity
import com.juanetoh.mostaza.api.model.ApiClient
import com.juanetoh.mostaza.api.model.Post
import com.juanetoh.mostaza.ui.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiClient: ApiClient,
) : ViewModel() {
    private val _postList: MutableLiveData<List<Post>> = MutableLiveData()
    val postList: LiveData<List<Post>> = _postList

    fun getPosts() {
        viewModelScope.launch {
            val posts = apiClient.getPosts()
            _postList.postValue(posts)
        }
    }

    fun navigate(context: Context, screen: Screens) {
        val intent = Intent(context, MainActivity::class.java).apply {
            action = screen.navigateAction
        }
        context.startActivity(intent)
    }
}
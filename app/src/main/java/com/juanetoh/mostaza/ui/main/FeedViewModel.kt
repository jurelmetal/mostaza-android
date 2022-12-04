package com.juanetoh.mostaza.ui.main

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.juanetoh.mostaza.R
import com.juanetoh.mostaza.api.ApiClient
import com.juanetoh.mostaza.api.model.Post
import com.juanetoh.mostaza.api.model.Response
import com.juanetoh.mostaza.extensions.readOnly
import com.juanetoh.mostaza.ui.util.CombinedLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SortBy {
    New,
    Old,
}

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val apiClient: ApiClient,
) : ViewModel() {

    enum class ErrorMessages(@StringRes val resId: Int) {
        ApiFailure(R.string.feed_get_posts_failure),
    }

    private val _sortBy = MutableLiveData(SortBy.New)
    private val _postList = MutableLiveData<List<Post>>()
    private val _errorToast = MutableLiveData<ErrorMessages>()

    val errorToast = _errorToast.readOnly()

    val sortedPostList: LiveData<List<Post>> = CombinedLiveData<List<Post>>(_postList, _sortBy) { (a1, a2) ->
        val list = a1 as? List<Post> ?: listOf()
        when(a2 as SortBy?) {
            SortBy.New -> list.sortedByDescending { it.creationDate }
            SortBy.Old -> list.sortedBy { it.creationDate }
            else -> list
        }
    }

    fun getPosts() {
        viewModelScope.launch {
            val posts = apiClient.getPosts()
            when(posts.status) {
                Response.Status.Success -> _postList.postValue(posts.value)
                Response.Status.Failure -> _errorToast.postValue(ErrorMessages.ApiFailure)
            }

        }
    }

    fun toggleSort() {
        viewModelScope.launch {
            val newVal = when(_sortBy.value) {
                SortBy.New -> SortBy.Old
                SortBy.Old -> SortBy.New
                else -> SortBy.New
            }
            _sortBy.postValue(newVal)
        }
    }
}
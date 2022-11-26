package com.juanetoh.mostaza.ui.main

import androidx.lifecycle.*
import androidx.navigation.NavController
import com.juanetoh.mostaza.api.model.ApiClient
import com.juanetoh.mostaza.api.model.Post
import com.juanetoh.mostaza.ui.Screens
import com.juanetoh.mostaza.ui.util.CombinedLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SortBy {
    New,
    Old,
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiClient: ApiClient,
) : ViewModel() {

    private val _sortBy: MutableLiveData<SortBy> = MutableLiveData(SortBy.New)
    private val _postList: MutableLiveData<List<Post>> = MutableLiveData()

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
            _postList.postValue(posts)
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
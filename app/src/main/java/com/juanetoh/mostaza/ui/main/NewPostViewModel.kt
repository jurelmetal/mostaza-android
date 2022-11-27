package com.juanetoh.mostaza.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanetoh.mostaza.R
import com.juanetoh.mostaza.api.ApiClient
import com.juanetoh.mostaza.api.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPostViewModel @Inject constructor(
    private val apiClient: ApiClient,
) : ViewModel() {

    private val _textEditContent: MutableLiveData<String> = MutableLiveData("")
    val textEditContent: LiveData<String> = _textEditContent

    private val _toastMessage: MutableLiveData<String> = MutableLiveData()
    val toastMessage: LiveData<String> = _toastMessage

    val navigationRequested: MutableLiveData<Int> = MutableLiveData()


    fun onContentTextUpdated(text: String) {
        _textEditContent.postValue(text)
    }

    fun doPost() {
        viewModelScope.launch {
            textEditContent.value?.let { content ->
                when (val response = apiClient.submitPost(content)) {
                    is Response.Success ->  {
                        _toastMessage.postValue("Post submitted!")
                        navigationRequested.postValue(R.id.feedFragment)
                    }
                    is Response.Failure -> _toastMessage.postValue("Error when sending post: $response")
                }
            }
        }
    }
}
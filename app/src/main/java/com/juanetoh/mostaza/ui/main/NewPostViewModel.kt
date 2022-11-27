package com.juanetoh.mostaza.ui.main

import androidx.lifecycle.*
import com.juanetoh.mostaza.api.ApiClient
import com.juanetoh.mostaza.api.model.Response
import com.juanetoh.mostaza.auth.Identity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPostViewModel @Inject constructor(
    private val apiClient: ApiClient,
    private val identity: Identity,
) : ViewModel() {

    private val _textEditContent: MutableStateFlow<String> = MutableStateFlow("")
    val textEditContent: StateFlow<String> = _textEditContent

    private val _textEditAuthor: MutableStateFlow<String> = MutableStateFlow("")
    val textEditAuthor: StateFlow<String> = _textEditAuthor

    private val _toastMessage: MutableLiveData<String> = MutableLiveData()
    val toastMessage: LiveData<String> = _toastMessage

    val dismissFragment: MutableLiveData<Boolean> = MutableLiveData()


    fun onContentTextUpdated(text: String) {
        _textEditContent.value = text
    }

    fun onAuthorNameUpdated(authorName: String) {
        _textEditAuthor.value = authorName
        if (authorName.isNotBlank()) {
            identity.updateName(authorName)
        }
    }

    fun getAuthor() = identity.currentIdentity.value.userName

    fun doPost() {
        viewModelScope.launch {
            when (val response = apiClient.submitPost(_textEditContent.value)) {
                is Response.Success ->  {
                    _toastMessage.postValue("Post submitted!")
                    dismissFragment.postValue(true)
                }
                is Response.Failure -> _toastMessage.postValue("Error when sending post: $response")
            }
        }
    }
}
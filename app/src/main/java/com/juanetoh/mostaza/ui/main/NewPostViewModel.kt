package com.juanetoh.mostaza.ui.main

import androidx.lifecycle.*
import com.juanetoh.mostaza.api.ApiClient
import com.juanetoh.mostaza.api.model.Response
import com.juanetoh.mostaza.auth.Identity
import com.juanetoh.mostaza.extensions.readOnly
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPostViewModel @Inject constructor(
    private val apiClient: ApiClient,
    private val identity: Identity,
) : ViewModel() {

    private val _textEditContent = MutableStateFlow("")
    private val _textEditAuthor = MutableStateFlow("")

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage = _toastMessage.readOnly()

    val dismissFragment = MutableLiveData<Boolean>()

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
            val response = apiClient.submitPost(_textEditContent.value)
            when (response.status) {
                Response.Status.Success ->  {
                    _toastMessage.postValue("Post ${response.value} submitted!")
                    dismissFragment.postValue(true)
                }
                Response.Status.Failure -> _toastMessage.postValue("Error when sending post: ${response.error}")
            }
        }
    }
}
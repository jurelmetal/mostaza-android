package com.juanetoh.mostaza.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.juanetoh.mostaza.storage.LocalStorage
import com.juanetoh.mostaza.storage.StorageKey
import kotlinx.serialization.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
data class IdentityData(
    val userId: String,
    val userName: String,
) {
    companion object {
        private const val INVALID_USER_ID = ""
        private const val ANONYMOUS_USER_NAME = "Anonymous"
        val Anonymous = IdentityData(INVALID_USER_ID, ANONYMOUS_USER_NAME)
    }
}

class Identity @Inject constructor(
    private val localStorage: LocalStorage,
) {
    private val _currentIdentity: MutableLiveData<IdentityData> = MutableLiveData(IdentityData.Anonymous)
    val currentIdentity: LiveData<IdentityData> = _currentIdentity

    init {
        loadIdentityFromStorage()
    }

    private fun loadIdentityFromStorage() {
        val data = localStorage.get<IdentityData>(StorageKey.LastIdentity)
        data?.let { _currentIdentity.postValue(it) }
    }
}
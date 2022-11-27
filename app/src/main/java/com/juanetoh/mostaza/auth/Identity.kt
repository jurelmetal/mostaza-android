package com.juanetoh.mostaza.auth

import com.juanetoh.mostaza.storage.LocalStorage
import com.juanetoh.mostaza.storage.StorageKey
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Inject

@Serializable
data class IdentityData(
    val userId: String,
    val userName: String,
) {
    companion object {
        private const val ANONYMOUS_USER_NAME = "Anonymous"
        val Anonymous = IdentityData(UUID.randomUUID().toString(), ANONYMOUS_USER_NAME)
    }
}

@ActivityRetainedScoped
class Identity @Inject constructor(
    private val localStorage: LocalStorage,
) {
    private val _currentIdentity: MutableStateFlow<IdentityData> = MutableStateFlow(IdentityData.Anonymous)
    val currentIdentity: StateFlow<IdentityData> = _currentIdentity

    private val logger = Logger.getLogger("Identity")

    init {
        loadIdentityFromStorage()
    }

    private fun loadIdentityFromStorage() {
        val data = localStorage.get<IdentityData>(StorageKey.LastIdentity)
        _currentIdentity.value = data ?: IdentityData.Anonymous
    }

    fun updateWith(identityData: IdentityData) {
        if(localStorage.save(StorageKey.LastIdentity, identityData)) {
            logger.log(Level.INFO, "Setting new identity data $identityData")
            _currentIdentity.value = identityData
        }
    }

    fun updateName(authorName: String) {
        val prevIdentity = _currentIdentity.value
        if (prevIdentity.userName == authorName) return
        val newIdentity = IdentityData(
            userId = prevIdentity.userId,
            userName = authorName
        )
        updateWith(newIdentity)
    }
}
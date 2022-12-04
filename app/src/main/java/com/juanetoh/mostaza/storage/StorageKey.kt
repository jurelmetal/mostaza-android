package com.juanetoh.mostaza.storage

import com.juanetoh.mostaza.auth.IdentityData
import kotlin.reflect.KClass

enum class StorageKey(val className: KClass<*>, val key: String) {
    LastIdentity(IdentityData::class, "lastIdentity")
}
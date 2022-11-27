package com.juanetoh.mostaza.storage

import com.juanetoh.mostaza.auth.IdentityData
import kotlin.reflect.KClass
import kotlin.reflect.KType

enum class StorageKey(val className: KClass<*>, val key: String) {
    LastIdentity(IdentityData::class, "lastIdentity")
}
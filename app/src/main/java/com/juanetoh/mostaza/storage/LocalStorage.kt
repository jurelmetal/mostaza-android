package com.juanetoh.mostaza.storage

interface LocalStorage {
    fun <T> get(key: StorageKey): T?
    fun <T> save(key: StorageKey, value: T): Boolean
}
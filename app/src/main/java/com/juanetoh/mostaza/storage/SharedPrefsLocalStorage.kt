package com.juanetoh.mostaza.storage

import android.content.Context
import com.juanetoh.mostaza.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
@OptIn(InternalSerializationApi::class)
class SharedPrefsLocalStorage @Inject constructor (
    @ApplicationContext val context: Context
) : LocalStorage {

    private val sharedPrefs = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)

    override fun <T> get(key: StorageKey): T? {
        val string = sharedPrefs.getString(key.key, null) ?: return null
        val serializer = key.className.serializer() as KSerializer<*>
        return Json.decodeFromString(serializer, string) as T
    }

    override fun <T> save(key: StorageKey, value: T): Boolean {
        val serializer = key.className.serializer() as KSerializer<T>
        return sharedPrefs
            .edit()
            .putString(key.key, Json.encodeToString(serializer, value))
            .commit()
    }

    companion object {
        const val PREFERENCES_KEY = BuildConfig.APPLICATION_ID + ".localStorage"
    }
}
package com.juanetoh.mostaza.api.model

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ApiClient @Inject constructor() {

    @OptIn(ExperimentalSerializationApi::class)
    val client: MostazaService by lazy {
        val contentType = MediaType.parse("application/json") ?: throw Exception()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
        retrofit.create(MostazaService::class.java)
    }

    suspend fun getPosts() = suspendCoroutine<List<Post>> { cont ->
        client.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                val body = response.body()
                body?.let { cont.resume(body) } ?: run { cont.resume(listOf()) }
            }
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                cont.resumeWithException(t)
            }
        })
    }

    companion object {
        const val BASE_URL = "https://gin-production-1651.up.railway.app/"
    }
}
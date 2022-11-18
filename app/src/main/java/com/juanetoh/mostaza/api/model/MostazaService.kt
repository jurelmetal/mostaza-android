package com.juanetoh.mostaza.api.model

import retrofit2.Call
import retrofit2.http.GET

interface MostazaService {
    @GET("/")
    fun getPosts(): Call<List<Post>>
}
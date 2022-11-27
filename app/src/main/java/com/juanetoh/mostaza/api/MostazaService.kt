package com.juanetoh.mostaza.api

import com.juanetoh.mostaza.api.model.NewPostRequest
import com.juanetoh.mostaza.api.model.Post
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MostazaService {
    @GET("/")
    fun getPosts(): Call<List<Post>>

    @POST("/new/post")
    fun submitPost(@Body post: NewPostRequest): Call<ResponseBody>
}
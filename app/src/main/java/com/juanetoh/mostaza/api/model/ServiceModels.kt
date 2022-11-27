package com.juanetoh.mostaza.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewPostRequest(
    @SerialName("Content") val content: String,
    @SerialName("AuthorID") val authorId: String,
)

open class Response<T> {
    class Success<Ty>(value: Ty) : Response<Ty>()
    class Failure(error: String) : Response<String>()
}
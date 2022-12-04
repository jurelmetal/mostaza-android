package com.juanetoh.mostaza.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewPostRequest(
    @SerialName("Content") val content: String,
    @SerialName("AuthorID") val authorId: String,
    @SerialName("DisplayName") val authorName: String,
)

open class Response<T>(
    val status: Status,
    val value: T,
    val error: String? = null,
) {
    enum class Status {
        Success,
        Failure
    }

    companion object {
        fun <T> success(value: T): Response<T> {
            return Response(Status.Success, value)
        }
        @Suppress("UNCHECKED_CAST")
        fun <T> failure(error: String): Response<T> {
           return Response(Status.Failure, Unit as T, error)
        }
    }
}
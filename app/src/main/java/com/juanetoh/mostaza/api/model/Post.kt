package com.juanetoh.mostaza.api.model

import com.juanetoh.mostaza.api.serialization.ZonedDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime

@Serializable
data class Post(
    @SerialName("_id")
    val id: String,
    @SerialName("AuthorID")
    val authorId: String,
    @SerialName("Content")
    val content: String,
    @Serializable(with = ZonedDateTimeSerializer::class)
    @SerialName("CreationDate")
    val creationDate: ZonedDateTime,
)

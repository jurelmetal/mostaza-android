package com.juanetoh.mostaza.api.model

import android.os.Parcelable
import com.juanetoh.mostaza.api.serialization.ZonedDateTimeSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime

@Serializable
@Parcelize
data class Post(
    @SerialName("ID")
    val id: String,
    @SerialName("AuthorID")
    val authorId: String,
    @SerialName("DisplayName")
    val displayName: String,
    @SerialName("Content")
    val content: String,
    @Serializable(with = ZonedDateTimeSerializer::class)
    @SerialName("CreationDate")
    val creationDate: ZonedDateTime,
) : Parcelable

package com.juanetoh.mostaza.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Converter

@Module
@InstallIn(SingletonComponent::class)
class SerializationModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        val contentType = MediaType.parse("application/json") ?: throw Exception()
        return Json.asConverterFactory(contentType)
    }
}
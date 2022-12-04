package com.juanetoh.mostaza.di

import android.content.Context
import com.juanetoh.mostaza.R
import com.juanetoh.mostaza.api.MostazaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

@Module(includes = [SerializationModule::class])
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }

    @Provides
    fun provideRetrofitObject(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .baseUrl(context.getString(R.string.endpoint_url))
            .build()
    }

    @Provides
    fun provideServiceClient(retrofitObject: Retrofit): MostazaService {
        return retrofitObject.create(MostazaService::class.java)
    }
}
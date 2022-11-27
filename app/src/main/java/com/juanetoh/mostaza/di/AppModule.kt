package com.juanetoh.mostaza.di

import android.content.Context
import com.juanetoh.mostaza.storage.LocalStorage
import com.juanetoh.mostaza.storage.SharedPrefsLocalStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityRetainedComponent::class)
class AppModule {

    @Provides
    fun providesLocalStorage(@ApplicationContext context: Context): LocalStorage = SharedPrefsLocalStorage(context)
}
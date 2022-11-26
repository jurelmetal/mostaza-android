package com.juanetoh.mostaza.di

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.juanetoh.mostaza.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object MainActivityModule {

    @Provides
    fun provideNavController(activity: Activity): NavController {
        return activity.findNavController(R.id.nav_host_fragment)
    }
}
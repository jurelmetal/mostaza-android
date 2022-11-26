package com.juanetoh.mostaza.ui

import android.content.Intent
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.juanetoh.mostaza.R
import com.juanetoh.mostaza.ui.main.MainFragment
import com.juanetoh.mostaza.ui.main.NewPostFragment

enum class Screens(@IdRes val id: Int) {
    Timeline(R.id.feedFragment),
    NewPost(R.id.newPostFragment)
}
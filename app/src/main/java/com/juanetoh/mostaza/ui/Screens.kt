package com.juanetoh.mostaza.ui

import android.content.Intent
import androidx.fragment.app.Fragment
import com.juanetoh.mostaza.ui.main.MainFragment
import com.juanetoh.mostaza.ui.main.NewPostFragment

enum class Screens(val fragmentSupplier: () -> Fragment) {
    Timeline(MainFragment::newInstance),
    NewPost(NewPostFragment::newInstance);

    val navigateAction: String
        get() = "com.juanetoh.mostaza.actions.$name"
}
package com.juanetoh.mostaza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.juanetoh.mostaza.ui.Screens
import com.juanetoh.mostaza.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val targetScreen = Screens.values()
            .find { it.navigateAction == intent?.action }
        targetScreen?.let { ts ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ts.fragmentSupplier())
                .commitNow()
        }
    }
}
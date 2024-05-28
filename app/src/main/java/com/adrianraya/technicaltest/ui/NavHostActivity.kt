package com.adrianraya.technicaltest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adrianraya.technicaltest.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_host)
    }
}
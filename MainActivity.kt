package com.example.newsreaderapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Delay for 2.5 seconds before launching MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, Headlines::class.java))
        }, 2500)
    }
}

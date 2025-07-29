package com.example.newsreaderapp

import android.os.Bundle
import android.transition.TransitionInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val titleTextView: TextView = findViewById(R.id.detailTitle)
        val contentTextView: TextView = findViewById(R.id.detailContent)
        val imageView: ImageView = findViewById(R.id.detailImage)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val image = intent.getStringExtra("image")

        titleTextView.text = title
        contentTextView.text = content
        Glide.with(this).load(image).into(imageView)

        window.sharedElementEnterTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.change_image_transform)
        window.sharedElementExitTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.change_image_transform)

    }
}

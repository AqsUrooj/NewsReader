package com.example.newsreaderapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritesActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var articleDao: FavoriteArticleDao
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        recyclerView = findViewById(R.id.favoritesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = FavoriteAdapter()
        recyclerView.adapter = adapter

        db = AppDatabase.getDatabase(this)
        articleDao = db.favoriteArticleDao()

        articleDao.getAllFavorites().observe(this) { articles ->
            adapter.setData(articles)
        }
    }
}


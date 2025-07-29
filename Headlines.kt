package com.example.newsreaderapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Headlines : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val apiKey = "c61f3d2b3d5cf31b1c213a8f6b1d1917" // Make sure it's valid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_headlines)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.newsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchNews()

    }

    private fun fetchNews() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gnews.io/api/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(NewsApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.getTopHeadlines(apiKey)
                Log.d("GNews", "Articles fetched: ${response.articles.size}")
                withContext(Dispatchers.Main) {
                    if (response.articles.isNotEmpty()) {
                        recyclerView.adapter = NewsAdapter(response.articles)
                        Toast.makeText(this@Headlines, "Loaded ${response.articles.size} articles", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@Headlines, "No articles found.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("GNews", "Error: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Headlines, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
    // 👇 Add this to show menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_toolbar_menu, menu)
        return true
    }

    // 👇 Handle menu item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorites -> {
                val intent = Intent(this, FavoritesActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

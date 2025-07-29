package com.example.newsreaderapp

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsAdapter(private val articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.articleTitle)
        val description: TextView = view.findViewById(R.id.articleContent)
        val image: ImageView = view.findViewById(R.id.articleImage)
        val favoriteIcon: ImageView = view.findViewById(R.id.favoriteIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
        holder.description.text=article.description

        Glide.with(holder.itemView.context).load(article.image).into(holder.image)

        val context = holder.itemView.context
        val db = AppDatabase.getDatabase(context).favoriteArticleDao()

        CoroutineScope(Dispatchers.Main).launch {
            article.isFavorite = db.isFavorite(article.url)
            holder.favoriteIcon.setImageResource(
                if (article.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
            )
        }

        holder.favoriteIcon.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                article.isFavorite = !article.isFavorite

                val favArticle = FavouriteArticle(
                    title = article.title,
                    description = article.description,
                    imageUrl = article.image,
                    url = article.url
                )

                if (article.isFavorite) {
                    db.insertFavorite(favArticle)
                } else {
                    db.deleteFavorite(favArticle)
                }

                CoroutineScope(Dispatchers.Main).launch {
                    holder.favoriteIcon.setImageResource(
                        if (article.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
                    )
                    Toast.makeText(
                        context,
                        if (article.isFavorite) "Added to favorites" else "Removed from favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("title", article.title)
                putExtra("description", article.description)
                putExtra("image", article.image)
                putExtra("content", article.content)

            }

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (context as Activity),
                holder.image,  // The shared ImageView
                "articleImageTransition"  // Transition name
            )
            context.startActivity(intent, options.toBundle())
        }


    }
}

package com.example.moviejson

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
    private val movieImage: ImageView = itemView.findViewById(R.id.movieImage)

    fun onBind(item: MovieItem) {
        titleTextView.text = item.title

        /**
        Picasso.get()
            .load(item.imageUrl)
            .resize(300, 300)
            .centerCrop()
            .into(movieImage)
        **/

        Glide.with(movieImage)
            .load(item.imageUrl)
            .into(movieImage)
    }
}
package com.example.moviejson

data class MovieItem(val movieJson: MovieJson) {

    val title = movieJson.title
    val imageUrl = movieJson.imageUrl
}
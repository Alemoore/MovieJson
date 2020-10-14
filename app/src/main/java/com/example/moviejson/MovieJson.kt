package com.example.moviejson

import com.google.gson.annotations.SerializedName

data class MovieJson(val id: Int, val title: String, @SerializedName("img") val imageUrl: String) {}

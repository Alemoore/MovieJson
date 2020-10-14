package com.example.moviejson

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movies")
    //fun getMovies(@Query("sortBy") sortBy: String): Call<List<MovieJson>>
    fun getMovies(): Call<List<MovieJson>>

    @GET("movies/{id}")
    fun getMovieById(@Path("id") id: Int) : Call<MovieJson>
}
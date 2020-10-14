package com.example.moviejson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var movieService: MovieService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val items = ArrayList<MovieItem>()
        loader.visibility = View.VISIBLE
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MovieAdapter(items, LayoutInflater.from(this))
        initRetrofit()

        movieService.getMovies().enqueue(object : Callback<List<MovieJson>> {
            override fun onResponse(
                call: Call<List<MovieJson>>,
                response: Response<List<MovieJson>>
            ) {
                if (response.isSuccessful) {
                    items.clear()
                    items.addAll(
                        response.body()!!.asSequence()
                            .map { movieJson -> MovieItem(movieJson) }
                            .toList())
                    recyclerView.adapter!!.notifyDataSetChanged()
                    loader.visibility = View.GONE
                } else {
                    Toast.makeText(this@MainActivity, "FAILURE", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<MovieJson>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "FAILURE", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

    private fun initRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        //интерцепторы добавляются по очереди
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
                //так можно добавлять токен для авторизации
            .addInterceptor { chain: Interceptor.Chain ->
                val request = chain.request().newBuilder().addHeader("X-Auth-Token", "AuthToken").build()
                chain.proceed(request)
            }
            //а вот так можно логгировать запросы хттп
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://my-json-server.typicode.com/denis-zhuravlev/json-placeholder/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        movieService = retrofit.create(MovieService::class.java)


    }
}
package com.example.demo.views.network


import com.example.demo.views.models.Root
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
    @GET("top-headlines?country=in&category=business&apiKey=be3ca02e91bb4dd7ac46fdcc7b577066")
    suspend fun getNews() : Response<Root>

}
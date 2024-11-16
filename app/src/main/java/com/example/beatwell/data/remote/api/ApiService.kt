package com.example.beatwell.data.remote.api

import com.example.beatwell.data.remote.response.FoodsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/foods")
    fun getFoods(): Call<FoodsResponse>
}
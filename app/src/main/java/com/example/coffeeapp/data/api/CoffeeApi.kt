package com.example.coffeeapp.data.api

import retrofit2.http.GET

interface CoffeeApi {
    @GET("/api")
    suspend fun getCoffees() :List<CoffeeDto>
}
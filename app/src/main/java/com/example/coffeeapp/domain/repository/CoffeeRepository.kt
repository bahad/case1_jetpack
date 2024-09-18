package com.example.coffeeapp.domain.repository

import com.example.coffeeapp.domain.model.CoffeeModel
import com.example.coffeeapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface CoffeeRepository {
    fun getCoffees(): Flow<Resource<List<CoffeeModel>>>
    suspend fun getCoffeesOnce(): List<CoffeeModel>
}
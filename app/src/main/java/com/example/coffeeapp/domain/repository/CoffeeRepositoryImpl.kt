package com.example.coffeeapp.domain.repository

import com.example.coffeeapp.data.api.CoffeeApi
import com.example.coffeeapp.data.api.toCoffee
import com.example.coffeeapp.domain.model.CoffeeModel
import com.example.coffeeapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CoffeeRepositoryImpl(
    private val api: CoffeeApi
) : CoffeeRepository {

    override fun getCoffees(): Flow<Resource<List<CoffeeModel>>> = flow {
        emit(Resource.Loading())
        try {
            val remoteCoffees = api.getCoffees()
            val coffees = remoteCoffees.map { it.toCoffee() }
            emit(Resource.Success(coffees))
        } catch (e: Exception) {
            emit(Resource.Error("Error fetching data: ${e.localizedMessage}"))
        }
    }

    override suspend fun getCoffeesOnce(): List<CoffeeModel> {
        val remoteCoffees = api.getCoffees()
        return remoteCoffees.map { it.toCoffee() }
    }
}
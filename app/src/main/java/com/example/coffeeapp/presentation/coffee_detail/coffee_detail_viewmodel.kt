package com.example.myapplication.presentation.coffee_detail

import androidx.lifecycle.ViewModel
import com.example.coffeeapp.domain.model.CoffeeModel
import com.example.coffeeapp.domain.repository.CoffeeRepository

class CoffeeDetailViewModel(
    private val repository: CoffeeRepository
) : ViewModel() {

    private var coffeeList: List<CoffeeModel>? = null

    suspend fun getCoffeeById(coffeeId: Int): CoffeeModel? {
        if (coffeeList == null) {
            coffeeList = repository.getCoffeesOnce()
        }
        return coffeeList?.find { it.id == coffeeId }
    }
}
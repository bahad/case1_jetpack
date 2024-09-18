package com.example.myapplication.presentation.coffee_list

import com.example.coffeeapp.domain.model.CoffeeModel

data class CoffeeListState(
    val coffees: List<CoffeeModel> = emptyList(),
    val filteredCoffees: List<CoffeeModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val searchQuery: String = ""
)
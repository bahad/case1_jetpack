package com.example.coffeeapp.domain.model

data class CoffeeModel(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val region: String,
    val weight: Int,
    val flavorProfile: List<String>,
    val grindOption: List<String>,
    val roastLevel: Int,
    val imageUrl: String
)
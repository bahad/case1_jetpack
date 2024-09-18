package com.example.coffeeapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coffees")
data class CoffeeEntity(
    @PrimaryKey val id: Int,
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
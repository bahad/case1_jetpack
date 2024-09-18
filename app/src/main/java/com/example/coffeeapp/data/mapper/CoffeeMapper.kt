package com.example.coffeeapp.data.mapper

import com.example.coffeeapp.data.api.CoffeeDto
import com.example.coffeeapp.data.local.entity.CoffeeEntity
import com.example.coffeeapp.domain.model.CoffeeModel

fun CoffeeDto.toCoffeeEntity(): CoffeeEntity = CoffeeEntity(
    id = id,
    name = name,
    description = description,
    price = price,
    region = region,
    weight = weight,
    flavorProfile = flavorProfile,
    grindOption = grindOption,
    roastLevel = roastLevel,
    imageUrl = imageUrl
)

fun CoffeeEntity.toCoffee(): CoffeeModel = CoffeeModel(
    id = id,
    name = name,
    description = description,
    price = price,
    region = region,
    weight = weight,
    flavorProfile = flavorProfile,
    grindOption = grindOption,
    roastLevel = roastLevel,
    imageUrl = imageUrl
)
package com.example.coffeeapp.data.api

import com.example.coffeeapp.domain.model.CoffeeModel
import com.google.gson.annotations.SerializedName

data class CoffeeDto(
    @SerializedName("_id") val _id: String,
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val region: String,
    val weight: Int,
    @SerializedName("flavor_profile") val flavorProfile: List<String>,
    @SerializedName("grind_option") val grindOption: List<String>,
    @SerializedName("roast_level") val roastLevel: Int,
    @SerializedName("image_url") val imageUrl: String
)

fun CoffeeDto.toCoffee(): CoffeeModel {
    return CoffeeModel(
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
}
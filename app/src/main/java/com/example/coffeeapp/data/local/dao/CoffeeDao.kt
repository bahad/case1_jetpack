package com.example.coffeeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coffeeapp.data.local.entity.CoffeeEntity

@Dao
interface CoffeeDao{
    @Query("SELECT * FROM coffees")
    suspend fun getAllCoffees(): List<CoffeeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffees(coffees: List<CoffeeEntity>)


}
package com.example.coffeeapp.presentation

import androidx.lifecycle.ViewModel
import com.example.coffeeapp.domain.model.CoffeeModel
import com.example.coffeeapp.domain.repository.CoffeeRepository
import com.example.coffeeapp.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SharedViewModel(
    private val repository: CoffeeRepository
) : ViewModel() {

    private val _coffees = MutableStateFlow<List<CoffeeModel>>(emptyList())
    val coffees: StateFlow<List<CoffeeModel>> = _coffees.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getCoffees().collect { result ->
                if (result is Resource.Success) {
                    _coffees.value = result.data ?: emptyList()
                }
            }
        }
    }
}
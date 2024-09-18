package com.example.myapplication.presentation.coffee_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeeapp.domain.model.CoffeeModel
import com.example.coffeeapp.domain.repository.CoffeeRepository
import com.example.coffeeapp.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.job
import kotlinx.coroutines.delay

class CoffeeListViewModel(
    private val repository: CoffeeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CoffeeListState())
    val state: StateFlow<CoffeeListState> = _state

    private var searchJob: Job? = null

    init {
        getCoffees()
    }

    private fun getCoffees() {
        viewModelScope.launch {
            repository.getCoffees().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        //   _state.value = CoffeeListState(coffees = result.data ?: emptyList())
                        val coffees = result.data ?: emptyList()
                        _state.value =
                            _state.value.copy(
                                coffees = coffees,
                                filteredCoffees = coffees,
                                isLoading = false
                            )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                        //   _state.value = CoffeeListState(isLoading = true)
                    }

                    is Resource.Error -> {
                        _state.value =
                            _state.value.copy(error = result.message ?: "Error", isLoading = false)
                    }
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            filterCoffees()
        }
    }

    private fun filterCoffees() {
        val query = _state.value.searchQuery.lowercase()
        val filtered = if (query.isBlank()) {
            _state.value.coffees
        } else {
            _state.value.coffees.filter { coffeeModel ->
                coffeeModel.name.lowercase().contains(query) ||
                        coffeeModel.description.lowercase().contains(query) ||
                        coffeeModel.region.lowercase().contains(query) ||
                        coffeeModel.flavorProfile.any { it.lowercase().contains(query) }
            }
        }
        _state.value = _state.value.copy(filteredCoffees = filtered)
    }

    fun onFavoriteToggle(coffeeModel: CoffeeModel) {
        val currentFavorites = _state.value.favoriteCoffees.toMutableSet()
        if (currentFavorites.contains(coffeeModel.id)) {
            currentFavorites.remove(coffeeModel.id)
        } else {
            currentFavorites.add(coffeeModel.id)
        }
        _state.value = _state.value.copy(favoriteCoffees = currentFavorites)
    }
}
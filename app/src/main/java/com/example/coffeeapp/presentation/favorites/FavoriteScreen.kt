package com.example.coffeeapp.presentation.favorites

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.myapplication.presentation.coffee_list.CoffeeListViewModel
import com.example.myapplication.presentation.coffee_list.Components.CoffeeItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: CoffeeListViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState()
    Scaffold(topBar = { TopAppBar(title = { Text(text = "Favorites") }) }) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            val favoriteCoffees =
                state.value.coffees.filter { state.value.favoriteCoffees.contains(it.id) }
            if (favoriteCoffees.isEmpty()) {
                Text(
                    text = "No Favorites Yet",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(items = favoriteCoffees, key = { it.id }) { coffee ->
                        CoffeeItem(
                            coffee = coffee,
                            navController = navController,
                            isFavorite = true,
                            onFavoriteClick = { viewModel.onFavoriteToggle(it) },
                            modifier = Modifier.animateItemPlacement(animationSpec = tween(300))
                        )
                    }
                }
            }
        }
    }
}
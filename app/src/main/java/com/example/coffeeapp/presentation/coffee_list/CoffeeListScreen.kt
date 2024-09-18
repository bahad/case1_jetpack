package com.example.myapplication.presentation.coffee_list

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.presentation.coffee_list.Components.CoffeeItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CoffeeListScreen(
    navController: NavController,
    viewModel: CoffeeListViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Coffee List") })
        }
    ) { padding ->

        Column(modifier = Modifier.padding(padding).fillMaxSize()) {

            OutlinedTextField(
                value = state.value.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text("Search") },
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        // Hide the keyboard or perform any action when the user presses the search button
                    }
                )
            )

            Box(modifier = Modifier.padding(padding)) {
                when {
                    state.value.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    state.value.error.isNotBlank() -> {
                        Text(
                            text = state.value.error,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(items = state.value.filteredCoffees, key = { it.id }) { coffee ->
                                val isFav = state.value.favoriteCoffees.contains(coffee.id)
                                CoffeeItem(
                                    coffee = coffee,
                                    navController = navController,
                                    isFavorite = isFav,
                                    onFavoriteClick = { viewModel.onFavoriteToggle(it) },
                                    modifier = Modifier.animateItemPlacement(
                                        animationSpec = tween(
                                            durationMillis = 2000
                                        )
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }


    }
}
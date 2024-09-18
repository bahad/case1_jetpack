package com.example.myapplication.presentation.coffee_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.coffeeapp.domain.model.CoffeeModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeDetailScreen(
    coffeeId: Int,
    navController: NavController,
    viewModel: CoffeeDetailViewModel = koinViewModel()
) {
    var coffee by remember { mutableStateOf<CoffeeModel?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(coffeeId) {
        scope.launch {
            coffee = viewModel.getCoffeeById(coffeeId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(coffee?.name ?: "Coffee Detail") },
                navigationIcon = {
                    // Add a back button if desired
                }
            )
        }
    ) { padding ->
        if (coffee != null) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(coffee!!.imageUrl),
                    contentDescription = coffee!!.name,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = coffee!!.description,
                    style = MaterialTheme.typography.bodySmall
                )
                // Display other coffee details as needed
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Loading...")
            }
        }
    }
}
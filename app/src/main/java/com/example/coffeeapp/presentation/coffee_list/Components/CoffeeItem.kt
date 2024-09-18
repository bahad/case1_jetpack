package com.example.myapplication.presentation.coffee_list.Components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.coffeeapp.domain.model.CoffeeModel

@Composable
fun CoffeeItem(
    coffee: CoffeeModel,
    isFavorite: Boolean,
    onFavoriteClick: (CoffeeModel) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(250.dp) // Fixed card height
            .clickable { navController.navigate("coffee_detail/${coffee.id}") },

        ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Image and favorite icon in a Box with fixed height
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(coffee.imageUrl),
                    contentDescription = coffee.name,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = {
                        onFavoriteClick(coffee)
                    },
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                ) {
                    val icon = if (isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    }
                    val tint by animateColorAsState(
                        if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                    Icon(
                        imageVector = icon,
                        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = tint
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Coffee name text
            Text(
                text = coffee.name,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Price text
            Text(
                text = "$${coffee.price}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
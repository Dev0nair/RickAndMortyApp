package com.igonris.rickandmortyapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

@Composable
fun TopBarComponent(
    navController: NavController,
    extraIcons: List<Pair<ImageVector, () -> Unit>> = emptyList(),
    extraContent: @Composable () -> Unit = {}
) {
    val backStack: NavBackStackEntry? = navController.previousBackStackEntry

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if(backStack == null) Arrangement.End else Arrangement.SpaceBetween
    ) {
        if(backStack != null) {
            IconButton(
                onClick = navController::navigateUp,
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Go back"
                )
            }
        }

        extraContent()

        extraIcons.map { extraIcon ->
            IconButton(
                onClick = { extraIcon.second() },
            ) {
                Icon(
                    extraIcon.first,
                    contentDescription = "Extra icon"
                )
            }
        }
    }
}
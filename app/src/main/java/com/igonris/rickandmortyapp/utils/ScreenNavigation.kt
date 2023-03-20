package com.igonris.rickandmortyapp.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.igonris.rickandmortyapp.presentation.characterdetails.CharacterDetailsScreen
import com.igonris.rickandmortyapp.presentation.home.HomeScreen

class ScreenNavigation(
    private val navHostController: NavHostController
) {

    @Composable
    fun MainNavigationHost() =
        NavHost(navController = navHostController, startDestination = "home") {
            composable(route = "home") {
                HomeScreen(navController = navHostController)
            }
            composable(route = "details/{idCharacter}") {
                CharacterDetailsScreen(navController = navHostController)
            }
        }


}
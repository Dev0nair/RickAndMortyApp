package com.igonris.rickandmortyapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.igonris.rickandmortyapp.presentation.characterdetails.CharacterDetailsScreen
import com.igonris.rickandmortyapp.presentation.home.HomeScreen
import com.igonris.rickandmortyapp.ui.theme.RickAndMortyAppTheme
import com.igonris.rickandmortyapp.utils.ScreenNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            val screens: ScreenNavigation = ScreenNavigation(navController)

            RickAndMortyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    screens.MainNavigationHost()
                }
            }
        }
    }
}
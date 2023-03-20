package com.igonris.rickandmortyapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.igonris.rickandmortyapp.presentation.characterdetails.CharacterDetailsScreen
import com.igonris.rickandmortyapp.presentation.home.HomeComponent
import com.igonris.rickandmortyapp.ui.theme.RickAndMortyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    val navController: NavHostController = rememberNavController()
                    
                    Column {
                        TopAppBar {

                        }
                        NavHost(modifier = Modifier.fillMaxSize(), navController = navController, startDestination = "home") {
                            composable(route = "home") {
                                HomeComponent(navController = navController)
                            }
                            composable(route = "details/{idCharacter}") {
                                val idCharacter: String = it.arguments?.getString("idCharacter") ?: ""
                                CharacterDetailsScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
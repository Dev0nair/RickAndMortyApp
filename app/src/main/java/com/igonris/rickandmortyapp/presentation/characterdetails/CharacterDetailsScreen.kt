package com.igonris.rickandmortyapp.presentation.characterdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.igonris.rickandmortyapp.data.entity.Character
import com.igonris.rickandmortyapp.ui.theme.RickAndMortyAppTheme

@Composable
fun CharacterDetailsScreen(
) {
    val viewModel: CharacterDetailsViewModel = hiltViewModel()
    val state: CharacterDetailsState by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        state.character?.let { CharacterDetailsView(character = it) }
        state.error?.let { Text(text = it) }
        if(state.loading) Text(text = "Cargando")
    }
}

@Composable
fun CharacterDetailsView(character: Character) {
    Text(character.name)
}

@Preview
@Composable
fun CharacterDetailsPreview() {
    RickAndMortyAppTheme {
        CharacterDetailsView(Character(0, "Prueba", "", ""))
    }
}
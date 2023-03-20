package com.igonris.rickandmortyapp.presentation.characterdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.igonris.rickandmortyapp.R
import com.igonris.rickandmortyapp.data.entity.Character
import com.igonris.rickandmortyapp.ui.components.TopBarComponent
import com.igonris.rickandmortyapp.ui.theme.LargerElevation
import com.igonris.rickandmortyapp.ui.theme.MediumSpacing
import com.igonris.rickandmortyapp.ui.theme.RickAndMortyAppTheme

@Composable
fun CharacterDetailsScreen(
    navController: NavHostController
) {
    val viewModel: CharacterDetailsViewModel = hiltViewModel()
    val state: CharacterDetailsState by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TopBarComponent(navController = navController)
        if(state.loading) CircularProgressIndicator()
        state.character?.let { CharacterDetailsView(character = it, modifier = Modifier.weight(1f)) }
        state.error?.let { Snackbar {
            Text(text = it)
        } }
    }
}

@Composable
fun CharacterDetailsView(character: Character, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.padding(MediumSpacing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            AsyncImage(
                modifier = Modifier.fillParentMaxWidth(0.9f),
                model = character.image,
                contentDescription = "${character.name} image"
            )
        }
        item {
            Spacer(modifier = Modifier.size(MediumSpacing))
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(0.75f),
                elevation = LargerElevation,
            ) {
                Column(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(MediumSpacing),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(text = stringResource(id = R.string.details_name, character.name))
                    Text(text = stringResource(id = R.string.details_gender, character.gender))
                    Text(text = stringResource(id = R.string.details_origin, character.origin))
                    Text(text = stringResource(id = R.string.details_status, character.status))
                    Text(text = stringResource(id = R.string.details_type, character.type))
                }
            }
        }
    }
}

@Preview
@Composable
fun CharacterDetailsPreview() {
    RickAndMortyAppTheme {
        CharacterDetailsView(Character(0, "Prueba", "", ""))
    }
}
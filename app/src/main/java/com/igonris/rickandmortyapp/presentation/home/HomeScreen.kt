package com.igonris.rickandmortyapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.igonris.rickandmortyapp.R
import com.igonris.rickandmortyapp.data.entity.Character
import com.igonris.rickandmortyapp.data.entity.SimpleCharacter
import com.igonris.rickandmortyapp.data.entity.filter.ApiCharacterFilter
import com.igonris.rickandmortyapp.ui.components.CharacterFilter
import com.igonris.rickandmortyapp.ui.components.TopBarComponent
import com.igonris.rickandmortyapp.ui.theme.LargeSpacing
import com.igonris.rickandmortyapp.ui.theme.MediumSpacing
import com.igonris.rickandmortyapp.ui.theme.RickAndMortyAppTheme
import com.igonris.rickandmortyapp.ui.theme.SmallSpacing
import com.igonris.rickandmortyapp.utils.ScreenNavigation
import com.igonris.rickandmortyapp.utils.goToCharacterDetail

@Composable
fun HomeScreen(
    navController: NavController
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state: HomeViewState by viewModel.state.collectAsState()
    val swipeRefreshState: SwipeRefreshState =
        rememberSwipeRefreshState(isRefreshing = state.loading)
    val searchedText: String by viewModel.searchedText.collectAsState()
    val filter: ApiCharacterFilter by viewModel.filter.collectAsState()

    Column(
        modifier = Modifier
            .padding(SmallSpacing)
            .fillMaxSize()
    ) {
        TopBarComponent(
            navController,
            extraIcons = listOf(Icons.Default.Edit to viewModel::toggleShowDialog)
        )
        state.error?.let { Snackbar { Text(text = it) } }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            value = searchedText,
            onValueChange = viewModel::searchValue,
            singleLine = true,
            placeholder = { Text(text = "Search character") }
        )
        Spacer(modifier = Modifier.size(SmallSpacing))
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = viewModel::onSwipe
        ) {
            CharacterListView(
                list = state.data,
                onClick = navController::goToCharacterDetail,
                onCharacterShown = viewModel::onCharacterScrolled
            )
        }

        if(state.showDialog) {
            CharacterFilter(
                actualFilter = filter,
                onClose = { newFilter ->
                    viewModel.setFilter(newFilter)
                    viewModel.toggleShowDialog()
                }
            )
        }
    }
}

@Composable
fun CharacterListView(
    list: List<SimpleCharacter>,
    onClick: (SimpleCharacter) -> Unit,
    onCharacterShown: (SimpleCharacter) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = list) { item ->
            CharacterListItemView(
                character = item,
                onClick = onClick
            )

            onCharacterShown(item)
        }
    }
}

@Composable
fun CharacterListItemView(character: SimpleCharacter, onClick: (SimpleCharacter) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(character) }
            .background(MaterialTheme.colors.primaryVariant, shape = MaterialTheme.shapes.medium)
            .padding(MediumSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.clip(MaterialTheme.shapes.medium),
            model = character.image,
            contentDescription = character.name,
            placeholder = painterResource(
                id = R.drawable.ic_launcher_background
            )
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LargeSpacing)
        ) {
            Text(text = character.name, style = MaterialTheme.typography.h1)
            Text(text = character.gender, style = MaterialTheme.typography.h2)
            Text(text = character.type, style = MaterialTheme.typography.body1)
        }
    }
}

@Preview
@Composable
fun HomeComponent_Preview() {
    RickAndMortyAppTheme {
        CharacterListView(
            list = (0..5).map { n ->
                SimpleCharacter(
                    n,
                    "Personaje $n",
                    image = "https://hips.hearstapps.com/es.h-cdn.co/fotoes/images/series-television/11-cosas-que-no-sabias-de-rick-y-morty/137666502-1-esl-ES/11-cosas-que-no-sabias-de-Rick-y-Morty.jpg?crop=1xw:0.75xh;center,top&resize=1200:*",
                    gender = "Gender",
                    type = "Tipo"
                )
            },
            onClick = {},
            onCharacterShown = {}
        )
    }
}
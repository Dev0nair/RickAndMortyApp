package com.igonris.rickandmortyapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.igonris.rickandmortyapp.R
import com.igonris.rickandmortyapp.data.entity.SimpleCharacter
import com.igonris.rickandmortyapp.data.entity.filter.ApiCharacterFilter
import com.igonris.rickandmortyapp.ui.components.CharacterFilter
import com.igonris.rickandmortyapp.ui.theme.*
import com.igonris.rickandmortyapp.utils.goToCharacterDetail
import com.igonris.rickandmortyapp.utils.isHorizontalOrientation

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
            .fillMaxSize()
    ) {
        state.error?.let { Snackbar { Text(text = it) } }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(
                    if (isHorizontalOrientation()) 0.25f else 0.4f
                )
                .background(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    shape = MaterialTheme.shapes.medium.copy(topStart = CornerSize(0.dp), topEnd = CornerSize(0.dp))
                ),
            elevation = LargeElevation
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MediumSpacing),
                verticalArrangement = Arrangement.Bottom
            ) {
                item {
                    if (isHorizontalOrientation()) {
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                            Image(
                                modifier = Modifier
                                    .fillParentMaxHeight()
                                    .padding(top = SmallSpacing),
                                painter = painterResource(id = R.drawable.rickandmorty),
                                contentDescription = "Rick And Morty"
                            )
                            SearchAndFilterView(
                                modifier = Modifier.weight(1f),
                                onSearchValue = viewModel::searchValue,
                                onFilterClick = viewModel::toggleShowDialog,
                                searchedText = searchedText,
                                searchText = R.string.home_welcome_text
                            )
                        }
                    } else {
                        Image(
                            modifier = Modifier
                                .fillParentMaxHeight(0.5f)
                                .align(Alignment.CenterHorizontally)
                                .padding(top = SmallSpacing),
                            painter = painterResource(id = R.drawable.rickandmorty),
                            contentDescription = "Rick And Morty"
                        )
                        Text(
                            text = stringResource(id = R.string.home_welcome_text),
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = SmallSpacing),
                        )
                        SearchAndFilterView(
                            modifier = Modifier.fillMaxWidth(),
                            onSearchValue = viewModel::searchValue,
                            onFilterClick = viewModel::toggleShowDialog,
                            searchedText = searchedText,
                            searchText = R.string.home_filter_search
                        )
                    }
                }
            }
        }
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = viewModel::onSwipe,
        ) {
            CharacterListView(
                list = state.data,
                onClick = navController::goToCharacterDetail,
                onCharacterShown = viewModel::onCharacterScrolled
            )
        }

        if (state.showDialog) {
            CharacterFilter(
                actualFilter = filter,
                onApply = { newFilter ->
                    viewModel.setFilter(newFilter)
                    viewModel.toggleShowDialog()
                },
                onCancel = viewModel::toggleShowDialog
            )
        }
    }
}

@Composable
fun SearchAndFilterView(modifier: Modifier = Modifier, onSearchValue: (String) -> Unit, onFilterClick: () -> Unit, searchText: Int, searchedText: String) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(top = SmallSpacing),
            shape = MaterialTheme.shapes.medium,
            value = searchedText,
            onValueChange = onSearchValue,
            singleLine = true,
            placeholder = { Text(text = stringResource(id = searchText)) }
        )
        IconButton(
            onClick = onFilterClick,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter_outlined),
                tint = MaterialTheme.colors.primary,
                contentDescription = "Filter"
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
            .fillMaxSize()
            .padding(horizontal = LargeSpacing),
        verticalArrangement = Arrangement.spacedBy(MediumSpacing),
    ) {
        item {
            Spacer(modifier = Modifier.size(MediumSpacing))
        }

        items(items = list) { character ->
            CharacterListItemView(
                character = character,
                onClick = onClick
            )

            onCharacterShown(character)
        }
    }
}

@Composable
fun CharacterListItemView(character: SimpleCharacter, onClick: (SimpleCharacter) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(character) },
        shape = MaterialTheme.shapes.medium,
        elevation = LargeElevation
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
                Text(text = character.name, style = MaterialTheme.typography.h2)
                Text(text = character.gender, style = MaterialTheme.typography.h3)
                Text(text = character.type, style = MaterialTheme.typography.body1)
            }
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
package com.igonris.rickandmortyapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.igonris.rickandmortyapp.data.entity.filter.ApiCharacterFilter
import com.igonris.rickandmortyapp.data.entity.filter.Gender
import com.igonris.rickandmortyapp.ui.theme.MediumElevation
import com.igonris.rickandmortyapp.ui.theme.MediumSpacing
import com.igonris.rickandmortyapp.ui.theme.SmallSpacing

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CharacterFilter(
    actualFilter: ApiCharacterFilter,
    onClose: (ApiCharacterFilter) -> Unit
) {
    val filter: MutableState<ApiCharacterFilter> = remember {
        mutableStateOf(actualFilter.copy())
    }

    Dialog(
        onDismissRequest = { onClose(filter.value) },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Card(
            elevation = MediumElevation,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .padding(MediumSpacing),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                GenderFilter(
                    onSelected = { selected -> filter.value = filter.value.copy(gender = selected) },
                    actualSelected = filter.value.gender
                )

                Row {
                    Button(onClick = { onClose(filter.value) }) {
                        Text("Aplicar")
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GenderFilter(onSelected: (Gender) -> Unit, actualSelected: Gender) {
    Column {
        Text(text = "Gender")
        Gender.values().map { gender ->
            Chip(
                onClick = { onSelected(gender) },
                enabled = true,
                content = { Text(gender.parseName()) },
            )
        }
    }
}
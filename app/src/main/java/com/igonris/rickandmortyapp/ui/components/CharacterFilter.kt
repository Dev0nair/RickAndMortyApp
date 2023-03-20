package com.igonris.rickandmortyapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.igonris.rickandmortyapp.data.entity.filter.ApiCharacterFilter
import com.igonris.rickandmortyapp.data.entity.filter.Gender
import com.igonris.rickandmortyapp.ui.theme.MediumElevation
import com.igonris.rickandmortyapp.ui.theme.MediumSpacing
import com.igonris.rickandmortyapp.utils.capitalizeFirstChar

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CharacterFilter(
    actualFilter: ApiCharacterFilter,
    onApply: (ApiCharacterFilter) -> Unit,
    onCancel: () -> Unit
) {
    val filter: MutableState<ApiCharacterFilter> = remember {
        mutableStateOf(actualFilter.copy())
    }

    Dialog(
        onDismissRequest = { onCancel() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Card(
            elevation = MediumElevation,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MediumSpacing)
            ) {
                GenderFilter(
                    onSelected = { selected -> filter.value = filter.value.copy(gender = selected) },
                    actualSelected = filter.value.gender
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = { onCancel() }) {
                        Text("Cancel")
                    }

                    Button(onClick = { onApply(filter.value) }) {
                        Text("Apply")
                    }
                }
            }
        }
    }

}

@Composable
fun GenderFilter(onSelected: (Gender) -> Unit, actualSelected: Gender) {
    Column {
        Text(text = "Gender", style = MaterialTheme.typography.h2)
        Gender.values().map { gender ->
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = CenterVertically) {
                RadioButton(
                    onClick = { onSelected(gender) },
                    selected = actualSelected == gender,
                )
                Text(modifier = Modifier.clickable { onSelected(gender) }, text = gender.name.capitalizeFirstChar(), style = MaterialTheme.typography.body1)
            }
        }
    }
}
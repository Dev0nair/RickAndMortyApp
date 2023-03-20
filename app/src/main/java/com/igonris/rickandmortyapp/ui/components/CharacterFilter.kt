package com.igonris.rickandmortyapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.igonris.rickandmortyapp.R
import com.igonris.rickandmortyapp.data.entity.filter.ApiCharacterFilter
import com.igonris.rickandmortyapp.ui.theme.MediumElevation
import com.igonris.rickandmortyapp.ui.theme.MediumSpacing

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
                    Button(onClick = { onCancel() }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)) {
                        Text(text = stringResource(id = R.string.button_cancel))
                    }

                    Button(onClick = { onApply(filter.value) }) {
                        Text(stringResource(id = R.string.button_apply))
                    }
                }
            }
        }
    }

}
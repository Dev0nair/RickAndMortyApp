package com.igonris.rickandmortyapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.igonris.rickandmortyapp.R
import com.igonris.rickandmortyapp.data.entity.filter.Gender
import com.igonris.rickandmortyapp.utils.capitalizeFirstChar

@Composable
fun GenderFilter(onSelected: (Gender) -> Unit, actualSelected: Gender) {
    Column {
        Text(text = stringResource(id = R.string.filter_gender), style = MaterialTheme.typography.h2)
        Gender.values().map { gender ->
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    onClick = { onSelected(gender) },
                    selected = actualSelected == gender,
                )
                Text(modifier = Modifier.clickable { onSelected(gender) }, text = gender.name.capitalizeFirstChar(), style = MaterialTheme.typography.body1)
            }
        }
    }
}
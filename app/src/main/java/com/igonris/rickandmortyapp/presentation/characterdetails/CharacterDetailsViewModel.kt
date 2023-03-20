package com.igonris.rickandmortyapp.presentation.characterdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igonris.rickandmortyapp.data.entity.Character
import com.igonris.rickandmortyapp.domain.IGetCharacterDetailsUseCase
import com.igonris.rickandmortyapp.utils.AppDispatchers
import com.igonris.rickandmortyapp.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val dispatchers: AppDispatchers,
    private val getCharacterDetailsUseCase: IGetCharacterDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state: MutableStateFlow<CharacterDetailsState> = MutableStateFlow(
        CharacterDetailsState()
    )
    val state: StateFlow<CharacterDetailsState> = _state

    init {
        loadData()
    }

    private fun loadData() {
        val idCharacter: String = savedStateHandle.get<String>("idCharacter") ?: ""

        viewModelScope.launch(dispatchers.io) {
            val result: Event<Character> = getCharacterDetailsUseCase(idCharacter)
            processResult(result)
        }
    }

    private fun processResult(result: Event<Character>) {
        viewModelScope.launch(dispatchers.main) {
            when(result) {
                is Event.Result -> _state.update { it.copy(loading = false, character = result.value, error = null) }
                is Event.Error -> _state.update { it.copy(error = result.message, loading = false) }
                else -> _state.update { it.copy(error = null, loading = false) }
            }
        }
    }
}
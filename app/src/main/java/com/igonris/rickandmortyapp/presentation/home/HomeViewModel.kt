package com.igonris.rickandmortyapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igonris.rickandmortyapp.data.entity.ListCharacterInfo
import com.igonris.rickandmortyapp.data.entity.SimpleCharacter
import com.igonris.rickandmortyapp.data.entity.filter.ApiCharacterFilter
import com.igonris.rickandmortyapp.domain.IGetCharactersListUseCase
import com.igonris.rickandmortyapp.utils.AppDispatchers
import com.igonris.rickandmortyapp.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dispatchers: AppDispatchers,
    private val getFilteredCharactersListUseCase: IGetCharactersListUseCase
) : ViewModel() {

    /* Private Vars */
    private var nextPage: Int? = 0

    private val _searchedText: MutableStateFlow<String> = MutableStateFlow("")
    private val _filter: MutableStateFlow<ApiCharacterFilter> = MutableStateFlow(ApiCharacterFilter())
    private val _state: MutableStateFlow<HomeViewState> = MutableStateFlow(HomeViewState())

    /* Public Vars */
    val searchedText: StateFlow<String> = _searchedText
    val filter: StateFlow<ApiCharacterFilter> = _filter
    val state: StateFlow<HomeViewState> = _state

    /* Public Methods */
    fun toggleShowDialog() {
        _state.update { it.copy(showDialog = !it.showDialog) }
    }

    fun onSwipe() {
        loadData(reset = true)
    }

    fun onCharacterScrolled(simpleCharacter: SimpleCharacter) {
        state.value.data.lastOrNull()?.let { lastCharacter ->
            if (lastCharacter.id == simpleCharacter.id) {
                loadData()
            }
        }
    }

    fun searchValue(value: String) {
        nextPage = 0
        _searchedText.update { value }
    }

    fun setFilter(filter: ApiCharacterFilter) {
        nextPage = 0
        _filter.update { filter }
    }

    /* Private Methods */
    init {
        _searchedText.debounce(350).combine(_filter) { actualSearchedText, actualFilter ->
            val newDataList: List<SimpleCharacter> = searchData(actualFilter.copy(name = actualSearchedText))
            _state.update { it.copy(data = newDataList) }
        }.launchIn(viewModelScope)
    }

    private fun loadData(reset: Boolean = false) {
        if (reset) nextPage = 0

        if (nextPage != null) {
            _filter.update { it.copy(actualPage = nextPage!!) }
        }
    }

    private suspend fun getData(
        filter: ApiCharacterFilter,
    ): ListCharacterInfo {
        val page: Int = nextPage ?: return ListCharacterInfo(0, emptyList())

        return withContext(dispatchers.io) {
            val event: Event<ListCharacterInfo> = getFilteredCharactersListUseCase(page, filter)

            return@withContext if (event is Event.Result<ListCharacterInfo>) event.value
            else ListCharacterInfo(0, emptyList())
        }
    }

    private suspend fun searchData(
        filter: ApiCharacterFilter
    ): List<SimpleCharacter> {
        // Checking if we got the following page. If it's null, it means we reached the last available page.
        return withContext(dispatchers.main) {
            if (nextPage != null) {
                val data: ListCharacterInfo = getData(filter = filter)

                // We only gettin nextPage == 0 when it's the first page or we want to reset,
                // so in that case, we gonna be cleaning the list with the result of the page 0
                val newList: List<SimpleCharacter> = if (nextPage == 0) {
                    data.list
                } else {
                    _state.value.data + data.list
                }

                nextPage = data.nextPage
                newList
            } else {
                emptyList()
            }
        }
    }

}
package com.igonris.rickandmortyapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igonris.rickandmortyapp.data.entity.ListCharacterInfo
import com.igonris.rickandmortyapp.data.entity.SimpleCharacter
import com.igonris.rickandmortyapp.data.entity.filter.ApiCharacterFilter
import com.igonris.rickandmortyapp.domain.GetFilteredCharactersListUseCase
import com.igonris.rickandmortyapp.utils.AppDispatchers
import com.igonris.rickandmortyapp.utils.processEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dispatchers: AppDispatchers,
    private val getFilteredCharactersListUseCase: GetFilteredCharactersListUseCase
) : ViewModel() {

    /* Private Vars */
    private var nextPage: Int? = 0

    private val _searchedText: MutableStateFlow<String> = MutableStateFlow("")
    private val _filter: MutableStateFlow<ApiCharacterFilter> = MutableStateFlow(ApiCharacterFilter())
    private val _state: MutableStateFlow<HomeViewState> = MutableStateFlow(HomeViewState())
    private val _searchedList: StateFlow<List<SimpleCharacter>> =
        combine(_searchedText.debounce(500L), _filter) { text, filter ->
            searchData(searchedText = text, filter = filter)
        }
            .flowOn(dispatchers.io)
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    /* Public Vars */
    val searchedText: StateFlow<String> = _searchedText
    val filter: StateFlow<ApiCharacterFilter> = _filter
    val state: StateFlow<HomeViewState> = combine(_searchedList, _state) { list, homeViewState ->
        homeViewState.copy(data = list, error = null)
    }
    .flowOn(dispatchers.main)
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), _state.value)

    /* Public Methods */

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
        loadData(reset = true)
    }

    private fun loadData(reset: Boolean = false) {
        if(reset) nextPage = 0

        if(nextPage != null) {
            _filter.update { it.copy(actualPage = nextPage!!) }
        }
    }

    private suspend fun getData(
        filter: ApiCharacterFilter,
        text: String
    ): ListCharacterInfo {
        val page: Int = nextPage ?: return ListCharacterInfo(null, emptyList())

        return processEvent(
            event = getFilteredCharactersListUseCase.execute(page, filter.copy(name = text)),
            onError = { errorMsg -> _state.update { it.copy(error = errorMsg) } },
            orElse = ListCharacterInfo(page, emptyList())
        )
    }

    private suspend fun searchData(
        searchedText: String,
        filter: ApiCharacterFilter
    ): List<SimpleCharacter> {
        // Checking if we got the following page. If it's null, it means we reached the last available page.
        if (nextPage != null) {
            _state.update { it.copy(loading = true) }
            val data: ListCharacterInfo = getData(filter = filter.copy(actualPage = nextPage!!), text = searchedText)
            _state.update { it.copy(loading = false) }

            // We only gettin nextPage == 0 when it's the first page or we want to reset,
            // so in that case, we gonna be cleaning the list with the result of the page 0
            val newList: List<SimpleCharacter> = if (nextPage == 0) {
                data.list
            } else {
                _searchedList.value + data.list
            }

            nextPage = data.nextPage
            return newList
        } else {
            return emptyList()
        }
    }

}
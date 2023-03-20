package com.igonris.rickandmortyapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.igonris.rickandmortyapp.data.entity.ListCharacterInfo
import com.igonris.rickandmortyapp.data.entity.SimpleCharacter
import com.igonris.rickandmortyapp.data.entity.filter.ApiCharacterFilter
import com.igonris.rickandmortyapp.data.entity.filter.Gender
import com.igonris.rickandmortyapp.domain.IGetCharactersListUseCase
import com.igonris.rickandmortyapp.presentation.home.HomeViewModel
import com.igonris.rickandmortyapp.utils.AppDispatchers
import com.igonris.rickandmortyapp.utils.Event
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import okhttp3.internal.immutableListOf
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBe
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val dispatchers = AppDispatchers(
        io = testDispatcher, main = testDispatcher, default = testDispatcher
    )

    // region ViewModel
    private val getCharactersListUseCase: IGetCharactersListUseCase = mock()
    private val homeViewModel: HomeViewModel by lazy { HomeViewModel(dispatchers, getCharactersListUseCase) }
    // endregion

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(getCharactersListUseCase)
        Dispatchers.resetMain()
    }

    @Test
    fun `check if data reloaded when updating filter`() = runTest {
        val filter: ApiCharacterFilter = ApiCharacterFilter()
        val expectedListValue: List<SimpleCharacter> = immutableListOf(
            SimpleCharacter(0, "hola1", Gender.female.parseName(), "", "")
        )
        val expectedValue: ListCharacterInfo = ListCharacterInfo(0, expectedListValue)

        whenever(getCharactersListUseCase(0, filter = filter)) doReturn Event.Result<ListCharacterInfo>(expectedValue)

        val job = homeViewModel.state.launchIn(backgroundScope)

        homeViewModel.setFilter(filter)

        advanceUntilIdle()

        verify(getCharactersListUseCase)(0, filter)
        homeViewModel.state.value.data shouldBe expectedListValue

        job.cancel()
    }


    @Test
    fun `check if data reloaded when searching for name`() = runTest {
        val filter: ApiCharacterFilter = ApiCharacterFilter("cualquiera")
        val expectedListValue: List<SimpleCharacter> = immutableListOf(
            SimpleCharacter(0, "hola2", Gender.female.parseName(), "", "")
        )
        val expectedValue: ListCharacterInfo = ListCharacterInfo(0, expectedListValue)

        whenever(getCharactersListUseCase(0, filter = filter)) doReturn Event.Result<ListCharacterInfo>(expectedValue)

        val job = homeViewModel.state.launchIn(backgroundScope)

        homeViewModel.searchValue("cualquiera")

        advanceUntilIdle()

        verify(getCharactersListUseCase)(0, filter)
        homeViewModel.state.value.data shouldBe expectedListValue

        job.cancel()
    }

    @Test
    fun `check if data reloaded when swiping`() = runTest {
        val filter: ApiCharacterFilter = ApiCharacterFilter("")
        val expectedListValue: List<SimpleCharacter> = immutableListOf(
            SimpleCharacter(0, "hola3", Gender.female.parseName(), "", "")
        )
        val expectedValue: ListCharacterInfo = ListCharacterInfo(0, expectedListValue)

        whenever(getCharactersListUseCase(0, filter = filter)) doReturn Event.Result<ListCharacterInfo>(expectedValue)

        val job = homeViewModel.state.launchIn(backgroundScope)

        homeViewModel.onSwipe()

        advanceUntilIdle()

        verify(getCharactersListUseCase)(0, filter)
        homeViewModel.state.value.data shouldBe expectedListValue

        job.cancel()
    }
}
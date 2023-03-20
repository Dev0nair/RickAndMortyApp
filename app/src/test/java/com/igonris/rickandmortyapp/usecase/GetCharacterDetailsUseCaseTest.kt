package com.igonris.rickandmortyapp.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.igonris.rickandmortyapp.data.entity.Character
import com.igonris.rickandmortyapp.data.entity.ListCharacterInfo
import com.igonris.rickandmortyapp.data.entity.SimpleCharacter
import com.igonris.rickandmortyapp.data.entity.filter.ApiCharacterFilter
import com.igonris.rickandmortyapp.data.entity.filter.Gender
import com.igonris.rickandmortyapp.data.entity.response.SingleCharacterResponse
import com.igonris.rickandmortyapp.data.repository.RickAndMortyAPI
import com.igonris.rickandmortyapp.domain.GetCharacterDetailsUseCase
import com.igonris.rickandmortyapp.domain.IGetCharacterDetailsUseCase
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
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBe
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
class GetCharacterDetailsUseCaseTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    // region ViewModel
    private val rickAndMortyAPI: RickAndMortyAPI = mock()
    private val getCharacterDetailsUseCase: IGetCharacterDetailsUseCase by lazy { GetCharacterDetailsUseCase(rickAndMortyAPI) }
    // endregion

    @After
    fun tearDown() {
        verifyNoMoreInteractions(rickAndMortyAPI)
    }

    @Test
    fun `check if data reloaded when updating filter`() = runTest {
        val characterDetail: Character = Character(id = 0, name = "Ricardo", image = "", gender = Gender.female.parseName())
        val characterResponse: SingleCharacterResponse = SingleCharacterResponse(id = characterDetail.id, name = characterDetail.name, gender = characterDetail.gender)

        whenever(rickAndMortyAPI.getCharacterDetails(characterDetail.id.toString())) doReturn characterResponse

        val result: Event<Character> = getCharacterDetailsUseCase(characterDetail.id.toString())

        verify(rickAndMortyAPI).getCharacterDetails(characterDetail.id.toString())
        result shouldBeInstanceOf Event.Result::class
        (result as Event.Result<Character>).value shouldBeEqualTo  characterDetail
    }
}
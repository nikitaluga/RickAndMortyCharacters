package com.rickandmortycharacters.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickandmortycharacters.data.CharactersRepository
import com.rickandmortycharacters.data.model.CharacterData
import com.rickandmortycharacters.data.model.CharacterEpisodeResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharactersRepository
) : ViewModel() {

    private val _charactersState = MutableLiveData<State>()
    val charactersState: LiveData<State>
        get() = _charactersState

    private var characterList = listOf<CharacterEpisodeResult>()
    private var isQueryAvailable: Boolean = false
    private val firstPage: Int = 1
    private var pageNumber: Int = firstPage

    init {
        getCharacters()
    }

    private fun getCharacters() {
        isQueryAvailable = true
        _charactersState.value = State.LoadState
        val episodesList = mutableListOf<String>()
        viewModelScope.launch {
            runCatching {
                val deferredCharacters = async {
                    repository.getCharacters()
                }
                val charactersResult = deferredCharacters.await()

                setEpisodeName(charactersResult, episodesList)

                combineCharactersAndEpisodes(charactersResult, episodesList)

            }.onFailure { throwable ->
                State.Error(throwable.message)
            }
        }
    }

    fun searchNextPage() {
        if (isQueryAvailable) {
            val currentCharacters: MutableList<CharacterEpisodeResult> =
                characterList.toMutableList()
            val episodesList = mutableListOf<String>()
            viewModelScope.launch {
                runCatching {
                    val deferredCharacters = async {
                        repository.getCharacters(pageNumber + 1)
                    }
                    val charactersResult = deferredCharacters.await()

                    setEpisodeName(charactersResult, episodesList)

                    combineCharactersAndEpisodesNextPage(
                        charactersResult,
                        episodesList,
                        currentCharacters
                    )

                    characterList = currentCharacters
                    _charactersState.postValue(
                        State.Success(currentCharacters)
                    )
                    pageNumber++

                }.onFailure { throwable ->
                    State.Error(throwable.message)
                }
            }
        }
    }

    private suspend fun setEpisodeName(
        listCharacters: List<CharacterData>,
        episodesList: MutableList<String>
    ) {
        for (character in listCharacters) {
            episodesList.add(
                repository.getEpisode(
                    character.episode.first().substringAfterLast("/").toInt()
                ).name
            )
        }
    }

    private fun combineCharactersAndEpisodes(
        listCharacters: List<CharacterData>,
        episodesList: MutableList<String>,
    ) {
        listCharacters.zip(episodesList) { character, episode ->
            CharacterEpisodeResult(character, episode)
        }.let { characterEpisodeList ->
            characterList = characterEpisodeList
            _charactersState.postValue(State.Success(characterEpisodeList))
            checkLastQuery(characterEpisodeList)
        }
    }

    private fun combineCharactersAndEpisodesNextPage(
        listCharacters: List<CharacterData>,
        episodesList: MutableList<String>,
        currentCharacter: MutableList<CharacterEpisodeResult>
    ) {
        listCharacters.zip(episodesList) { character, episode ->
            CharacterEpisodeResult(character, episode)
        }.let { characterEpisodeList ->
            currentCharacter.addAll(characterEpisodeList)
            checkLastQuery(characterEpisodeList)
        }
    }

    private fun checkLastQuery(characters: List<CharacterEpisodeResult>?) {
        if (characters != null) {
            if (characters.size % 20 != 0) {
                isQueryAvailable = false
            }
        } else
            isQueryAvailable = false
    }

    sealed class State {
        data class Success(val listCharacterData: List<CharacterEpisodeResult>) : State()
        data class Error(val errorMessage: String?) : State()
        object LoadState : State()
    }
}
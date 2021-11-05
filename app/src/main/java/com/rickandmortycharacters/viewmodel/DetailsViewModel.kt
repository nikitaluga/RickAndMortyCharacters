package com.rickandmortycharacters.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickandmortycharacters.data.CharactersRepository
import com.rickandmortycharacters.data.model.CharacterData
import com.rickandmortycharacters.data.model.CharacterLocationResult
import com.rickandmortycharacters.data.model.LocationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: CharactersRepository
) : ViewModel() {

    private val _characterState = MutableLiveData<State>()
    val characterState: LiveData<State>
        get() = _characterState

    fun getCharacter(id: Int) {
        viewModelScope.launch {
            _characterState.postValue(State.LoadState)
            runCatching {
                val deferredCharacter = async {
                    try {
                        repository.getCharacter(id)
                    } catch (ex: Exception) {
                        CharacterData()
                    }
                }
                val deferredLocation = async {
                    try {
                        repository.getLocation(id)
                    } catch (ex: Exception) {
                        LocationResponse()
                    }
                }
                val resultCharacter = deferredCharacter.await()
                val resultLocation = deferredLocation.await()

                _characterState.postValue(
                    State.Success(
                        CharacterLocationResult(
                            resultCharacter,
                            resultLocation
                        )
                    )
                )
            }.onFailure {
                _characterState.postValue(State.Error(it.message))
            }
        }
    }

    sealed class State {
        data class Success(val characterResult: CharacterLocationResult) : State()
        data class Error(val errorMessage: String?) : State()
        object LoadState : State()
    }
}
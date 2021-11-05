package com.rickandmortycharacters.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.rickandmortycharacters.data.model.CharacterEpisodeResult

class CharactersAdapter(itemClick: (character: CharacterEpisodeResult) -> Unit) :
    AsyncListDifferDelegationAdapter<CharacterEpisodeResult>(CharactersDiffUtil()) {

    init {
        delegatesManager.addDelegate(CharactersAdapterDelegate(itemClick))
    }

    class CharactersDiffUtil : DiffUtil.ItemCallback<CharacterEpisodeResult>() {
        override fun areItemsTheSame(
            oldItem: CharacterEpisodeResult,
            newItem: CharacterEpisodeResult
        ): Boolean {
            return oldItem.character.id == newItem.character.id
        }

        override fun areContentsTheSame(
            oldItem: CharacterEpisodeResult,
            newItem: CharacterEpisodeResult
        ): Boolean {
            return oldItem == newItem
        }
    }
}
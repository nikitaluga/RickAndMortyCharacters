package com.rickandmortycharacters.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.rickandmortycharacters.R
import com.rickandmortycharacters.data.model.CharacterEpisodeResult
import com.rickandmortycharacters.databinding.ItemCardCharacterBinding
import com.rickandmortycharacters.enum.CharacterStatus

class CharactersAdapterDelegate(private val itemClick: (characterData: CharacterEpisodeResult) -> Unit) :
    AbsListItemAdapterDelegate<CharacterEpisodeResult, CharacterEpisodeResult, CharactersAdapterDelegate.ViewHolder>() {

    override fun isForViewType(
        item: CharacterEpisodeResult,
        items: MutableList<CharacterEpisodeResult>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(
                R.layout.item_card_character,
                parent,
                false
            ),
            itemClick
        )
    }

    override fun onBindViewHolder(
        item: CharacterEpisodeResult,
        holder: ViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class ViewHolder(
        itemView: View,
        private val itemClick: (character: CharacterEpisodeResult) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private lateinit var binding: ItemCardCharacterBinding

        fun bind(characterData: CharacterEpisodeResult) {
            binding = ItemCardCharacterBinding.bind(itemView)
            binding.image.clipToOutline = true

            with(binding) {
                Glide
                    .with(itemView)
                    .load(characterData.character.image)
                    .placeholder(R.drawable.ic_emoticon)
                    .error(R.drawable.ic_error)
                    .into(image)

                name.text = characterData.character.name
                when (characterData.character.status) {
                    CharacterStatus.ALIVE.value -> indicator.setImageResource(R.drawable.ic_green_indicator)
                    CharacterStatus.DEAD.value -> indicator.setImageResource(R.drawable.ic_red_indicator)
                    else -> indicator.setImageResource(R.drawable.ic_yellow_indicator)
                }
                status.text = characterData.character.status
                species.text = characterData.character.species
                location.text = characterData.character.location.name
                episode.text = characterData.episode
            }

            selectCharacters(characterData)
        }

        private fun selectCharacters(characterData: CharacterEpisodeResult) {
            itemView.setOnClickListener {
                itemClick(characterData)
            }
        }
    }
}
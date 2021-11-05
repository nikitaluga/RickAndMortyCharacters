package com.rickandmortycharacters.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.rickandmortycharacters.R
import com.rickandmortycharacters.data.model.CharacterLocationResult
import com.rickandmortycharacters.databinding.DetailsFragmentBinding
import com.rickandmortycharacters.enum.CharacterStatus
import com.rickandmortycharacters.util.toast
import com.rickandmortycharacters.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {

    private val args: DetailsFragmentArgs by navArgs()
    private val binding: DetailsFragmentBinding by viewBinding(DetailsFragmentBinding::bind)
    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCharacter(args.characterId)
        characterObserve(view)
    }

    private fun characterObserve(view: View) {
        viewModel.characterState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DetailsViewModel.State.Success -> {
                    isVisibleView(true)
                    bindPhotoLayout(view, state.characterResult)
                    bindInfoLayout(state.characterResult)
                }
                is DetailsViewModel.State.Error -> {
                    isVisibleView(false)
                    toast("$state")
                }
                is DetailsViewModel.State.LoadState -> {
                    isVisibleView(false)
                }
            }
        }
    }

    private fun isVisibleView(isVisible: Boolean) {
        with(binding) {
            characterPhotoLayout.root.isVisible = isVisible
            characterInfoLayout.root.isVisible = isVisible
            progressBar.isVisible = !isVisible
        }
    }

    private fun bindPhotoLayout(view: View, characterResult: CharacterLocationResult) {
        with(binding.characterPhotoLayout) {
            Glide
                .with(view)
                .load(characterResult.character.image)
                .placeholder(R.drawable.ic_emoticon)
                .error(R.drawable.ic_error)
                .into(image)
            name.text = characterResult.character.name
            when (characterResult.character.status) {
                CharacterStatus.ALIVE.value -> indicator.setImageResource(R.drawable.ic_green_indicator)
                CharacterStatus.DEAD.value -> indicator.setImageResource(R.drawable.ic_red_indicator)
                else -> indicator.setImageResource(R.drawable.ic_yellow_indicator)
            }
            status.text = characterResult.character.status
            species.text = characterResult.character.species

            back.setOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun bindInfoLayout(characterResult: CharacterLocationResult) {
        with(binding.characterInfoLayout) {
            gender.text = characterResult.character.gender
            origin.text = characterResult.character.origin.name
            location.text = characterResult.location.name
            type.text = characterResult.location.type
            dimensions.text = characterResult.location.dimension
            episodes.text = characterResult.character.episode.size.toString()
        }
    }
}
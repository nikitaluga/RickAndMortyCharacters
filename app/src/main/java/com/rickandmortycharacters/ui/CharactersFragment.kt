package com.rickandmortycharacters.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rickandmortycharacters.R
import com.rickandmortycharacters.databinding.CharactersFragmentBinding
import com.rickandmortycharacters.ui.adapter.CharactersAdapter
import com.rickandmortycharacters.util.CharactersItemDecoration
import com.rickandmortycharacters.util.autoCleared
import com.rickandmortycharacters.util.toast
import com.rickandmortycharacters.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment(R.layout.characters_fragment) {

    private val viewModel: CharactersViewModel by viewModels()
    private val binding: CharactersFragmentBinding by viewBinding(CharactersFragmentBinding::bind)
    private var adapterCharacters: CharactersAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initRecyclerView()
        collectingRemoteCharacters()
    }

    private fun collectingRemoteCharacters() {
        viewModel.charactersState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CharactersViewModel.State.Success -> {
                    adapterCharacters.items = state.listCharacterData
                    isVisibleView(true)
                }
                is CharactersViewModel.State.Error -> {
                    isVisibleView(false)
                    toast("$state")
                }
                is CharactersViewModel.State.LoadState -> isVisibleView(false)
            }
        }
    }

    private fun isVisibleView(isVisible: Boolean) {
        with(binding) {
            rvCharacters.isVisible = isVisible
            progressBar.isVisible = !isVisible
        }
    }

    private fun initAdapter() {
        adapterCharacters = CharactersAdapter { personage ->
            val action = CharactersFragmentDirections.actionCharactersFragmentToDetailsFragment(
                personage.character.id
            )
            findNavController().navigate(action)
        }
    }

    private fun initRecyclerView() {
        with(binding.rvCharacters) {
            adapter = adapterCharacters
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(CharactersItemDecoration())

            addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        if (!recyclerView.canScrollVertically(1)) {
                            viewModel.searchNextPage()
                        }
                    }
                }
            )
        }
    }
}
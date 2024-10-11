package com.example.rickandmortyapi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortyapi.CharacterAdapter
import com.example.rickandmortyapi.CharacterViewModel
import com.example.rickandmortyapi.network.model.Character
import com.example.rickandmortyapi.databinding.FragmentCharacterBinding
import com.example.rickandmortyapi.utils.Resource
import com.example.rickandmortyapi.utils.gone
import com.example.rickandmortyapi.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : Fragment() {

    private val binding by lazy {
        FragmentCharacterBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[CharacterViewModel::class.java]
    }

    private val characterAdapter by lazy {
        CharacterAdapter {
                character -> onClicked(character)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.characters.observe(viewLifecycleOwner) { characters ->
            when (characters) {
                is Resource.Error -> {
                    showToast(characters.message)
                    showProgressBar(false)
                }

                is Resource.Loading -> showProgressBar(true)

                is Resource.Success -> {
                    showProgressBar(false)
                    characterAdapter.submitList(characters.data)
                }
            }
        }
    }

    private fun setupRecyclerView() = with(binding.rvCharacters) {
        layoutManager = LinearLayoutManager(context)
        adapter = characterAdapter
    }

    private fun showProgressBar(isVisible: Boolean) = with(binding) {
        if (isVisible) progressBar.visible() else progressBar.gone()
    }

    private fun showToast(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun onClicked(character: Character) {
        val action = CharacterFragmentDirections.actionCharacterFragmentToDetailFragment(character.id)
        findNavController().navigate(action)
    }
}
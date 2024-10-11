package com.example.rickandmortyapi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.FragmentCharacterDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import coil.load
import com.example.rickandmortyapi.network.model.Character


@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private val binding by lazy {
        FragmentCharacterDetailBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterId = arguments?.getInt("characterId") ?: return
        viewModel.setCharacterId(characterId)

        viewModel.characterDetails.observe(viewLifecycleOwner) { character ->
            character?.let {
                bind(it)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                showToast(it)
            }
        }
    }

    private fun bind(character: Character) = with(binding) {
        tvName.text = character.name
        tvStatus.text = "${character.status} - ${character.species}"
        tvCurrentLocation.text = character.location.name
        tvCurrentGender.text = character.gender

        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            .format(Date())
        tvCurrentTime.text = currentTime


        binding.imgDetail.load(character.image) {
            placeholder(R.drawable.ic_launcher_background)
        }

        val circleDrawable = when (character.status) {
            "Alive" -> R.drawable.green
            "Dead" -> R.drawable.red
            else -> R.drawable.grey
        }
        imgCircle.setImageResource(circleDrawable)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
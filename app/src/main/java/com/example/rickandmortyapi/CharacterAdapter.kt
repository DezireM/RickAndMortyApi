package com.example.rickandmortyapi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapi.databinding.ItemCharacterBinding
import com.example.rickandmortyapi.data.model.Character
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.ListAdapter
import com.example.rickandmortyapi.R

class CharacterAdapter(
    private val onClick: (Character) -> Unit
) : ListAdapter<Character, CharacterAdapter.CharacterViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) = with(binding) {
            tvName.text = character.name
            tvStatus.text = "${character.status} - ${character.species}"
            tvCurrentLocation.text = character.location.name

            val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                .format(Date())
            tvCurrentTime.text = currentTime

            Glide.with(root.context)
                .load(character.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgCartoon)

            val circleDrawable = when (character.status) {
                "Alive" -> R.drawable.green
                "Dead" -> R.drawable.red
                else -> R.drawable.grey
            }
            imgCircle.setImageResource(circleDrawable)

            root.setOnClickListener {
                onClick(character)
            }
        }
    }
}

private val diffUtil = object : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem == newItem
}
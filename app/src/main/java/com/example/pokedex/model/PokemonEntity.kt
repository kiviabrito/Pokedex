package com.example.pokedex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val photos: List<String>,
    val abilities: List<String>,
    val forms: List<String>, // NOT HIDDEN
    val height: Int,
    val held_items: List<String>,
    val is_default: Boolean,
    val moves: List<String>,
    val order: Int,
    val species: String,
    val stats: List<Stat>,
    val types: List<String>,
    val weight: Int
)
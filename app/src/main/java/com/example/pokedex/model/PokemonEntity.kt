package com.example.pokedex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val photos: List<String?>,
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
) {
    override fun equals(other: Any?): Boolean {
        other as PokemonEntity

        if (id != other.id) {
            return false
        }
        if (name != other.name) {
            return false
        }
        if (photos != other.photos) {
            return false
        }
        if (abilities != other.abilities) {
            return false
        }
        if (forms != other.forms) {
            return false
        }
        if (height != other.height) {
            return false
        }
        if (held_items != other.held_items) {
            return false
        }
        if (is_default != other.is_default) {
            return false
        }
        if (moves != other.moves) {
            return false
        }
        if (order != other.order) {
            return false
        }
        if (species != other.species) {
            return false
        }
        if (stats != other.stats) {
            return false
        }
        if (types != other.types) {
            return false
        }
        if (weight != other.weight) {
            return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + photos.hashCode()
        result = 31 * result + abilities.hashCode()
        result = 31 * result + forms.hashCode()
        result = 31 * result + height
        result = 31 * result + held_items.hashCode()
        result = 31 * result + is_default.hashCode()
        result = 31 * result + moves.hashCode()
        result = 31 * result + order
        result = 31 * result + species.hashCode()
        result = 31 * result + stats.hashCode()
        result = 31 * result + types.hashCode()
        result = 31 * result + weight
        return result
    }
}
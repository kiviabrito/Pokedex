package com.example.pokedex.model

class PokemonInfo(
    val abilities: List<Ability>,
    val base_experience: Int,
    val forms: List<Detail>,
    val game_indices: List<GameIndex>,
    val height: Int,
    val held_items: List<HeldItem>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<Move>,
    val name: String,
    val order: Int,
    val species: Detail,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
) {
    fun createPokemonEntity(): PokemonEntity {
        val abilities = mutableListOf<String>()
        this.abilities.forEach { ability ->
            if (ability.is_hidden.not()) {
                abilities.add(ability.ability.name)
            }
        }
        val forms = mutableListOf<String>()
        this.forms.forEach { form ->
            forms.add(form.name)
        }
        val heldItems = mutableListOf<String>()
        this.held_items.forEach { item ->
            heldItems.add(item.item.name)
        }
        val moves = mutableListOf<String>()
        this.moves.forEach { move ->
            moves.add(move.move.name)
        }
        val types = mutableListOf<String>()
        this.types.forEach { type ->
            types.add(type.type.name)
        }
        return PokemonEntity(
            id = this.id,
            name = this.name,
            photos = listOf(
                this.sprites.front_default,
                this.sprites.front_shiny,
                this.sprites.back_shiny,
                this.sprites.front_shiny
            ),
            abilities = abilities,
            forms = forms,
            height = this.height,
            held_items = heldItems,
            is_default = this.is_default,
            moves = moves,
            order = this.order,
            species = this.species.name,
            stats = this.stats,
            types = types,
            weight = this.height
        )
    }
}

data class Ability(
    val ability: Detail,
    val is_hidden: Boolean,
    val slot: Int
)

data class GameIndex(
    val game_index: Int,
    val version: Detail
)

data class HeldItem(
    val item: Detail,
    val version_details: List<VersionDetail>
)

data class VersionDetail(
    val rarity: Int,
    val version: Detail
)

data class Move(
    val move: Detail,
    val version_group_details: List<VersionGroupDetail>
)

data class VersionGroupDetail(
    val level_learned_at: Int,
    val move_learn_method: Detail,
    val version_group: Detail
)

data class Sprites(
    val back_default: String,
    val back_female: String?,
    val back_shiny: String,
    val back_shiny_female:String?,
    val front_default: String,
    val front_female: String?,
    val front_shiny: String,
    val front_shiny_female: String?
)

data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: Detail
)

data class Type(
    val slot: Int,
    val type: Detail
)

data class Detail(
    val name: String,
    val url: String
)
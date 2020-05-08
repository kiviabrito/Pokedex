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
)

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
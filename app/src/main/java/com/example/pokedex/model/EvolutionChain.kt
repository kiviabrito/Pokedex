package com.example.pokedex.model

class EvolutionChain (
    val baby_trigger_item: Detail?,
    val chain: Chain,
    val id: Int
)

data class Chain(
    val evolution_detail: List<EvolutionDetail>,
    val evolves_to: List<Chain>,
    val is_baby: Boolean,
    val species: Detail
)

data class EvolutionDetail(
    val gender: Int?,
    val held_item: HeldItem?,
    val item: Detail?,
    val known_move: Boolean?,
    val known_move_type: Move?,
    val location: Detail?,
    val min_affection: Int?,
    val min_beauty: Int?,
    val min_happiness: Int?,
    val min_level: Int?,
    val needs_overworld_rain: Boolean,
    val party_species: Detail?,
    val party_type: Detail?,
    val relative_physical_stats: Detail?,
    val time_of_day: String,
    val trade_species:Detail?,
    val trigger: Detail,
    val turn_upside_down: Boolean
)


package com.example.pokedex.model

data class SpeciesInfo (
    val base_happiness: Int,
    val capture_rate: Int,
    val color: Detail,
    val egg_groups: List<Detail>,
    val evolution_chain: URLString,
    val evolves_from_species: Detail,
    val flavor_text_entries: List<Flavor>,
    val form_descriptions: List<Detail>,
    val forms_switchable: Boolean,
    val gender_rate: Int,
    val genera: List<Genera>,
    val generation: Detail,
    val growth_rate: Detail,
    val habitat: Detail,
    val has_gender_differences: Boolean,
    val hatch_counter: Int,
    val id: Int,
    val has_baby: Boolean,
    val name: String,
    val names: List<Name>,
    val order: Int,
    val pal_park_encounters: List<PalParkEncounter>,
    val pokedex_numbers: List<PokedexNum>,
    val shape: Detail,
    val varieties: List<Variety>
)

data class URLString(
    val url: String
)

data class Flavor(
    val flavor_text: String,
    val language: Detail,
    val version: Detail
)

data class Genera(
    val genus: String,
    val language: Detail
)

data class Name(
    val language: Detail,
    val name: String
)

data class PalParkEncounter(
    val area: Detail,
    val base_score: Int,
    val rate: Int
)

data class PokedexNum(
    val entry_number: Int,
    val pokedex: Detail
)

data class Variety(
    val is_default: Boolean,
    val pokemon: Detail
)
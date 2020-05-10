package com.example.pokedex.model

class AbilityInfo(
  val effect_changes: List<AbilityEffectChange>,
  val effect_entries: List<VerboseEffect>,
  val flavor_text_entries: List<Flavor>,
  val generation: Detail,
  val id: Int,
  val is_main_series: Boolean,
  val name: String,
  val names: List<Name>,
  val pokemon: List<Pokemon>
)

data class AbilityEffectChange(
  val effect_entries: List<Effect>,
  val version_group: Detail
)

data class Effect(
  val effect: String,
  val language: Detail
)

data class VerboseEffect(
  val effect: String,
  val language: Detail,
  val short_effect: String
)

data class Pokemon(
  val is_hidden: Boolean,
  val pokemon: Detail,
  val slot: Int
)